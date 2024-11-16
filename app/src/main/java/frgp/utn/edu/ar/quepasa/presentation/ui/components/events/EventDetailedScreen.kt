package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.images.ImagesListPreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.EventPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun EventDetailedScreen(navController: NavHostController, eventId: UUID) {
    val user by LocalAuth.current.collectAsState()
    val context = LocalContext.current
    val viewModel: EventViewModel = hiltViewModel()
    val eventPictureViewModel: EventPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    val event by viewModel.event.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEventById(eventId)
        eventPictureViewModel.getPicturesByEvent(eventId, 0, 10)
        pictureViewModel.setPicturesBitmap(eventPictureViewModel.picturesIds.value)
    }

    val bitmaps = pictureViewModel.pictures.collectAsState()

    if (event != null) {
        BaseComponent(
            navController = navController,
            user = user.user,
            title = "Detalle Evento",
            back = true
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

                        }
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

                        if(bitmaps.value.isNotEmpty()) {
                            ImagesListPreview(bitmaps = bitmaps.value)
                        }

                        Row {
                            //CardButtonsBar(event!!.id!!, event!!.votes!!)
                        }
                    }
                }
                Row {
                    /* TODO Comentarios */
                }
            }
        }
    }
}
