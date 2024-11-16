package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    description: String,
    icon: Int,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    tint: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        contentPadding = PaddingValues(0.dp),
        colors = colors
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = description,
            tint = tint
        )
    }
}