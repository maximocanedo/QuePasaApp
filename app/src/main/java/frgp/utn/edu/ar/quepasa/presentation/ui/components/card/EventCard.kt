package frgp.utn.edu.ar.quepasa.presentation.ui.components.card

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.presentation.ui.components.card.components.CardButtonsBar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText

@Composable
fun EventCard(event: Event) {
    var context = LocalContext.current
    ElevatedCard(
        onClick = {
            Toast.makeText(
                context,
                "Card Clicked",
                Toast.LENGTH_SHORT
            ).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(R.drawable.baseline_broken_image_24),
                    modifier = Modifier
                        .size(100.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(10.dp)
                        ),
                    contentDescription = "Event Image",
                    contentScale = ContentScale.Crop
                )
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
                ReadMoreText(
                    "Lorem ipsum odor amet, consectetuer adipiscing elit. Gravida nisi non himenaeos dis neque; sollicitudin quisque dui. Malesuada eleifend dapibus odio facilisis nibh metus massa.",
                    Modifier.padding(16.dp), MaterialTheme.typography.bodyLarge, 1, 10
                )
            }
        }
        event.votes?.let { CardButtonsBar(event, it) }
    }
}