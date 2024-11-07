package frgp.utn.edu.ar.quepasa.presentation.ui.components.card.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R

@Composable
fun UpVoteButton(text: String, onClick: () -> Unit) {
    val density = LocalDensity.current
    val style: TextStyle = MaterialTheme.typography.bodySmall
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    val initialWidthDp: Dp = remember(text) {
        with(density) {
            textMeasurer.measure(
                text = text,
                style = style,
            ).size.width.toDp() + 50.dp
        }
    }
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .widthIn(min = 10.dp)
            .width(initialWidthDp),
        colors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_upward_24),
                    contentDescription = "Up Vote",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Column {
                Text(
                    modifier = Modifier,
                    text = text,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}