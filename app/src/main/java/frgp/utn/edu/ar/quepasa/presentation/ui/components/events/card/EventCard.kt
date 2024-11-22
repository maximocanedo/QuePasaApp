package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.Bitmap
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.CardButtonsBar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText

@Composable
fun EventCard(
    bitmap: Bitmap?,
    navController: NavHostController,
    event: Event,
    user: User?,
    assists: Boolean,
    onAssistanceClick: () -> Unit,
    onEventAddToCalendar: () -> Unit,
    onRemoveClick: () -> Unit,
    onUpvoteClick: () -> Unit,
    onDownvoteClick: () -> Unit
) {
    val neighbourhoodNames: List<String> = event.neighbourhoods?.map { it.name } ?: emptyList()

    ElevatedCard(
        onClick = {
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
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_broken_image_24),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentScale = ContentScale.Crop
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.location),
                        contentDescription = "Ubicación",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = neighbourhoodNames.joinToString(", "),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                event.description?.let {
                    ReadMoreText(
                        it,
                        Modifier.padding(6.dp),
                        MaterialTheme.typography.bodyLarge,
                        1,
                        10
                    )
                }
            }
        }
        if(user?.role != Role.USER) {
            event.votes?.let { voteCount ->
                event.id?.let {
                    CardButtonsBar(
                        event,
                        user,
                        voteCount,
                        navController,
                        assists,
                        onAssistanceClick,
                        onEventAddToCalendar,
                        onRemoveClick,
                        onUpvoteClick,
                        onDownvoteClick,
                    )
                }
            }
        }
    }
}