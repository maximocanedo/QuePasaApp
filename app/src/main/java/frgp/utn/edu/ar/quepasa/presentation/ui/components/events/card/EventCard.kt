package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.CardButtonsBar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.EventPictureViewModel

@Composable
fun EventCard(navController: NavHostController, event: Event) {
    val context = LocalContext.current
    val eventPictureViewModel: EventPictureViewModel = hiltViewModel()
    val pictures by eventPictureViewModel.pictures.collectAsState()
    val img = pictures.content.firstOrNull()?.id?.let {
        "http://canedo.com.ar:8080/api/pictures/$it/view"
    }

    LaunchedEffect(Unit) {
        event.id?.let { eventPictureViewModel.getPicturesByEvent(it, 0, 10) }
    }
    ElevatedCard(
        onClick = {
            Toast.makeText(
                context,
                "Card Clicked",
                Toast.LENGTH_SHORT
            ).show()
            /* TODO Detalles y comentarios on click */
            navController.navigate("eventDetailedScreen/${event.id}")
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                if (img != null) {
                    AsyncImage(
                        model = (img),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(10.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_broken_image_24),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(10.dp)
                            )
                    )
                }
            }
            Column {
                event.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(6.dp),
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
                event.description?.let {
                    ReadMoreText(
                        it,
                        Modifier.padding(16.dp), MaterialTheme.typography.bodyLarge,
                        1,
                        10
                    )
                }
            }
        }
        event.votes?.let { event.id?.let { id -> CardButtonsBar(id, it) } }
    }
}