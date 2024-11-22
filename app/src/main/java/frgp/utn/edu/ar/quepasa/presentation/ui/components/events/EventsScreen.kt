package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.EventCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.EventCategoryField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.EventPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(navController: NavHostController) {
    val user by LocalAuth.current.collectAsState()
    val viewModel: EventViewModel = hiltViewModel()
    val eventPictureViewModel: EventPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()

    val events by viewModel.events.collectAsState()
    val eventRvsps by viewModel.eventRvsps.collectAsState()
    val pictures by eventPictureViewModel.eventPictures.collectAsState()
    val eventPictureDTO by pictureViewModel.eventPictureDTO.collectAsState()

    var category by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var eventToDelete by remember { mutableStateOf<UUID?>(null) }

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val actualElements by viewModel.actualElements.collectAsState()
    val totalElements by viewModel.totalElements.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (user.isAdmin) {
            viewModel.getEvents()
        } else {
            viewModel.getEventsByNeighbourhood(user.user?.neighbourhood?.id!!)
        }
        viewModel.sortEventsByVotes()
    }

    LaunchedEffect(Unit, events) {
        viewModel.getRvspsByUser()
        viewModel.sortEventsByVotes()
        viewModel.viewModelScope.launch {
            events.content.forEach { event ->
                if (pictures.find { it.event?.id == event.id } == null) {
                    eventPictureViewModel.setEventsPicture(event.id!!)
                }
            }
        }
    }

    LaunchedEffect(pictures) {
        pictureViewModel.viewModelScope.launch {
            pictures.forEach { picture ->
                picture.event?.id?.let { uuid ->
                    if (eventPictureDTO.find { it?.eventId == picture.event?.id } == null) {
                        pictureViewModel.setPictureEvents(
                            picture.id,
                            uuid,
                        )
                    }
                }
            }
        }
    }

    BaseComponent(navController, "Listado Eventos", false) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                coroutineScope.launch {
                    user.user?.neighbourhood?.id?.let { viewModel.refreshEvents(user.isAdmin, it) }
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = search,
                        onValueChange = {
                            search = it
                            viewModel.viewModelScope.launch {
                                if (user.isAdmin) {
                                    viewModel.getEvents(it)
                                } else {
                                    user.user?.neighbourhood?.id?.let { id ->
                                        viewModel.getEventsByNeighbourhood(id, it)
                                    }
                                }
                                category = ""
                            }
                        },
                        label = {
                            Text("Buscar")
                        },
                        placeholder = {
                            Text("Curso de ...")
                        }
                    )
                }
                Row {
                    EventCategoryField(
                        modifier = Modifier.fillMaxWidth(),
                        category = category,
                        onItemSelected = {
                            category = it
                            viewModel.viewModelScope.launch {
                                if (user.isAdmin) {
                                    viewModel.getEventsByCategory(EventCategory.valueOf(it))
                                } else {
                                    user.user?.neighbourhood?.id?.let { id ->
                                        viewModel.getEventsByNeighbourhoodAndCategory(
                                            id,
                                            EventCategory.valueOf(it)
                                        )
                                    }
                                }
                                search = ""
                            }
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    userScrollEnabled = true,
                ) {
                    items(events.content) { event ->
                        key(event.id) {
                            EventCard(
                                eventPictureDTO.find { it?.eventId == event.id }?.bitmap,
                                navController,
                                event,
                                user.user,
                                assists = eventRvsps.find { it.event?.id == event.id } != null,
                                onAssistanceClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.rsvpEvent(event.id!!)
                                        user.user?.let {
                                            resetEvents(
                                                it,
                                                viewModel,
                                                actualElements,
                                                category,
                                                search
                                            )
                                        }
                                        viewModel.getRvspsByUser()
                                    }
                                },
                                onEventAddToCalendar = {
                                    val title = event.title ?: "Evento"
                                    val description = event.description ?: "Descripción"
                                    val beginTime = event.start?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                                        ?: System.currentTimeMillis()
                                    val endTime = event.end?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                                        ?: (beginTime + 3600000)

                                    val intent = Intent(Intent.ACTION_INSERT).apply {
                                        data = CalendarContract.Events.CONTENT_URI
                                        putExtra(CalendarContract.Events.TITLE, title)
                                        putExtra(CalendarContract.Events.DESCRIPTION, description)
                                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                                        putExtra(CalendarContract.Events.EVENT_LOCATION, event.address.toString()?:"Ubicación no especificada")
                                    }

                                    try {
                                        context.startActivity(intent)
                                        Toast.makeText(context, "Agrega el evento al calendario", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al abrir el calendario: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onRemoveClick = {
                                    eventToDelete = event.id
                                    showDialog = true
                                },
                                onUpvoteClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.upVote(event.id!!)
                                        user.user?.let {
                                            resetEvents(
                                                it,
                                                viewModel,
                                                actualElements,
                                                category,
                                                search
                                            )
                                        }
                                    }
                                },
                                onDownvoteClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.downVote(event.id!!)
                                        user.user?.let {
                                            resetEvents(
                                                it,
                                                viewModel,
                                                actualElements,
                                                category,
                                                search
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                    item {
                        if (isLoadingMore) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        } else {
                            if (actualElements < totalElements) {
                                Button(
                                    onClick = {
                                        viewModel.viewModelScope.launch {
                                            user.user?.neighbourhood?.let {
                                                viewModel.loadMoreEvents(
                                                    user.isAdmin,
                                                    it.id
                                                )
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text("Cargar más")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar eliminación") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar este evento?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            eventToDelete?.let { event ->
                                viewModel.deleteEvent(event)
                                user.user?.let {
                                    resetEvents(
                                        it,
                                        viewModel,
                                        actualElements,
                                        category,
                                        search
                                    )
                                }
                            }
                            eventToDelete = null
                            showDialog = false
                        }
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        )
    }
}

fun resetEvents(
    user: User,
    viewModel: EventViewModel,
    actualElements: Int,
    category: String,
    search: String
) {
    viewModel.viewModelScope.launch {
        if (user.role.toString() == "ADMIN") {
            if (category.isNotBlank()) {
                viewModel.getEventsByCategory(
                    EventCategory.valueOf(category),
                    size = actualElements
                )
            } else if (search.isNotBlank()) {
                viewModel.getEvents(search, size = actualElements)
            } else {
                viewModel.getEvents(size = actualElements)
            }
        } else {
            if (category.isNotBlank()) {
                viewModel.getEventsByNeighbourhoodAndCategory(
                    user.neighbourhood?.id!!,
                    EventCategory.valueOf(category),
                    size = actualElements
                )
            } else if (search.isNotBlank()) {
                user.neighbourhood?.let {
                    viewModel.getEventsByNeighbourhood(
                        it.id,
                        search,
                        size = actualElements
                    )
                }
            } else {
                viewModel.getEventsByNeighbourhood(user.neighbourhood?.id!!, size = actualElements)
            }
        }
        viewModel.sortEventsByVotes()
    }
}
