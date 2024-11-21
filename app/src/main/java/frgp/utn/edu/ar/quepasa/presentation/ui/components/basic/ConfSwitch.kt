package frgp.utn.edu.ar.quepasa.presentation.ui.components.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ConfSwitch(
    modifier: Modifier,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    loading: Boolean = false
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (checked)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surfaceContainer,
        label = "Background Color Animation"
    )
    Box(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .clip(RoundedCornerShape(48.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { if(!loading) onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f) 
            )
            if(loading) CircularProgressIndicator()
            else Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}


@Preview @Composable
fun ConfSwitchPreview() {
    var x by remember { mutableStateOf(false) }
    var l by remember { mutableStateOf(false) }
    ConfSwitch(
        Modifier,
        "Habilitar configuración",
        x,
        {
            CoroutineScope(IO).launch {
                l = true
                delay(3000)
                l = false
                x = it
            }
        },
        l
    )
}