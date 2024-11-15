package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.EventCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.EventCategoryField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.EventPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(navController: NavHostController) {
    val user by LocalAuth.current.collectAsState()
    val viewModel: EventViewModel = hiltViewModel()
    val eventPictureViewModel: EventPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()
    val events by viewModel.events.collectAsState()
    val pictures by eventPictureViewModel.eventPictures.collectAsState()
    val eventPictureDTO by pictureViewModel.eventPictureDTO.collectAsState()

    var category by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }

    LaunchedEffect(Unit, events) {
        viewModel.viewModelScope.launch {
            events.content.forEach { event ->
                eventPictureViewModel.setEventsPicture(event.id!!)
            }
        }
    }

    LaunchedEffect(pictures) {
        pictureViewModel.viewModelScope.launch {
            pictures.forEach { picture ->
                picture.event?.id?.let {
                    pictureViewModel.setPictureEvents(
                        picture.id,
                        it,
                    )
                }
            }
            pictureViewModel.setEventPictureDTO(eventPictureDTO)
        }
    }


    BaseComponent(navController, user.user, "Listado Eventos", false) {
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
                            viewModel.getEvents(search)
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
                            viewModel.getEventsByCategory(EventCategory.valueOf(it))
                            search = ""
                        }
                    }
                )
            }
            Row {
                LazyColumn(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = true,
                ) {
                    items(events.content) { event ->
                        key(event.id) {
                            EventCard(
                                eventPictureDTO.find { it?.eventId == event.id }?.bitmap,
                                navController,
                                event,
                                user.user,
                                onAssistanceClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.rsvpEvent(event.id!!)
                                    }
                                },
                                onRemoveClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.deleteEvent(event.id!!)
                                        resetEvents(viewModel, category, search)
                                    }
                                },
                                onUpvoteClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.upVote(event.id!!)
                                        resetEvents(viewModel, category, search)
                                    }
                                },
                                onDownvoteClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.downVote(event.id!!)
                                        resetEvents(viewModel, category, search)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun resetEvents(
    viewModel: EventViewModel,
    category: String,
    search: String
) {
    viewModel.viewModelScope.launch {
        if (category.isNotBlank()) {
            viewModel.getEventsByCategory(
                EventCategory.valueOf(
                    category
                )
            )
        } else if (search.isNotBlank()) {
            viewModel.getEvents(search)
        } else {
            viewModel.getEvents()
        }
    }
}