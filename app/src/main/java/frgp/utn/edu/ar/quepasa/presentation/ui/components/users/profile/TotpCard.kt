package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TotpCard(
    modifier: Modifier = Modifier
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .wrapContentHeight()
    ) {
        Text(
            text = "Avanzado",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(12.dp)
        )

    }
}

@Preview
@Composable
fun TotpCardPreview() {

}