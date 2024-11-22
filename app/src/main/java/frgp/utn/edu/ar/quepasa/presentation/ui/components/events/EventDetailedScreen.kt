package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.comment.CommentDialog
import frgp.utn.edu.ar.quepasa.presentation.ui.components.comment.EventCommentCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.CardButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.CardButtonsBar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.images.ImagesListPreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.commenting.CommentViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.EventPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun EventDetailedScreen(navController: NavHostController, eventId: UUID) {
    val user by LocalAuth.current.collectAsState()
    val viewModel: EventViewModel = hiltViewModel()
    val eventPictureViewModel: EventPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()
    val commentViewModel: CommentViewModel = hiltViewModel()

    val comments by viewModel.comments.collectAsState()
    val eventRvsp by viewModel.eventRvsps.collectAsState()
    var commentDialogState by remember { mutableStateOf(false) }
    var commentEditState by remember { mutableStateOf(false) }
    var commentEditUUID by remember { mutableStateOf(UUID.randomUUID()) }
    var commentEditText by remember { mutableStateOf("") }
    val context = LocalContext.current

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    val event by viewModel.event.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEventById(eventId)
        eventPictureViewModel.getPicturesByEvent(eventId, 0, 10)
        pictureViewModel.setPicturesBitmap(eventPictureViewModel.picturesIds.value)
        viewModel.getComments(eventId, 0, 10)
        viewModel.getRvspsByUser()
    }

    val bitmaps = pictureViewModel.pictures.collectAsState()

    if (event != null) {
        BaseComponent(
            navController = navController,
            title = "Detalle Evento",
            back = true,
            backRoute = "events"
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row {
                            event!!.title?.let { Text(text = it, fontSize = 24.sp) }
                        }
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            modifier = Modifier.height(24.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                event!!.start?.let {
                                    Text(
                                        text = it.format(formatter),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            VerticalDivider(
                                modifier = Modifier.padding(4.dp),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                event!!.end?.let {
                                    Text(
                                        text = it.format(formatter),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            event!!.address?.let {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = it,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            event!!.description?.let {
                                Text(
                                    text = it,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        if (bitmaps.value.isNotEmpty()) {
                            ImagesListPreview(bitmaps = bitmaps.value)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CardButton(
                                icon = R.drawable.baseline_add_comment_24,
                                description = "Comentar",
                                onClick = {
                                    commentDialogState = true
                                }
                            )
                            CardButtonsBar(
                                event = event!!,
                                user = user.user,
                                navController = navController,
                                voteCount = event!!.votes!!,
                                assists = eventRvsp.find { it.event?.id == event!!.id }?.confirmed
                                    ?: false,
                                onAssistanceClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.rsvpEvent(event!!.id!!)

                                        viewModel.getRvspsByUser()
                                    }

                                    val title = event!!.title ?: "Evento"
                                    val description = event!!.description ?: "Descripción"
                                    val beginTime = event!!.start?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                                        ?: System.currentTimeMillis()
                                    val endTime = event!!.end?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                                        ?: (beginTime + 3600000)

                                    val intent = Intent(Intent.ACTION_INSERT).apply {
                                        data = CalendarContract.Events.CONTENT_URI
                                        putExtra(CalendarContract.Events.TITLE, title)
                                        putExtra(CalendarContract.Events.DESCRIPTION, description)
                                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                                        putExtra(CalendarContract.Events.EVENT_LOCATION, "Ubicación no especificada")
                                    }

                                    try {
                                        context.startActivity(intent)
                                        Toast.makeText(context, "Agrega el evento a tu calendario", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al abrir el calendario: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                ,
                                onRemoveClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.deleteEvent(event!!.id!!)
                                        viewModel.getEventById(eventId)
                                    }
                                },
                                onUpvoteClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.upVote(event!!.id!!)
                                        viewModel.getEventById(eventId)
                                    }
                                },
                                onDownvoteClick = {
                                    viewModel.viewModelScope.launch {
                                        viewModel.downVote(event!!.id!!)
                                        viewModel.getEventById(eventId)
                                    }
                                }
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) { Text("Comentarios", style = MaterialTheme.typography.bodyMedium) }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    if (comments.content.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = true
                        ) {
                            items(comments.content) { comment ->
                                key(comment.id) {
                                    user.user?.let {
                                        EventCommentCard(
                                            comment = comment,
                                            voteCount = comment.votes,
                                            user = it,
                                            onUpvoteClick = {
                                                viewModel.viewModelScope.launch {
                                                    commentViewModel.upVoteComment(comment.id)
                                                    viewModel.getComments(eventId, 0, 10)
                                                    viewModel.sortCommentsByVotes()
                                                }
                                            },
                                            onDownvoteClick = {
                                                viewModel.viewModelScope.launch {
                                                    commentViewModel.downVoteComment(comment.id)
                                                    viewModel.getComments(eventId, 0, 10)
                                                    viewModel.sortCommentsByVotes()
                                                }
                                            },
                                            onDeleteClick = {
                                                viewModel.viewModelScope.launch {
                                                    commentViewModel.deleteComment(comment.id)
                                                    viewModel.getComments(eventId, 0, 10)
                                                    viewModel.sortCommentsByVotes()
                                                }
                                            },
                                            onEditClick = {
                                                viewModel.viewModelScope.launch {
                                                    commentEditUUID = comment.id
                                                    commentEditText = comment.content
                                                    commentEditState = true
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Text("No hay comentarios", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
        if (commentDialogState) {
            CommentDialog(
                onDismissRequest = { commentDialogState = false },
                onConfirm = { content ->
                    viewModel.viewModelScope.launch {
                        viewModel.comment(eventId, content)
                        viewModel.getComments(eventId, 0, 10)
                        viewModel.sortCommentsByVotes()
                    }
                    commentDialogState = false
                }
            )
        }
        if (commentEditState) {
            CommentDialog(
                content = commentEditText,
                onDismissRequest = { commentEditState = false },
                onConfirm = { content ->
                    viewModel.viewModelScope.launch {
                        commentViewModel.updateComment(commentEditUUID, content)
                        viewModel.getComments(eventId, 0, 10)
                        viewModel.sortCommentsByVotes()
                    }
                    commentEditText = ""
                    commentEditState = false
                }
            )
        }
    }
}
