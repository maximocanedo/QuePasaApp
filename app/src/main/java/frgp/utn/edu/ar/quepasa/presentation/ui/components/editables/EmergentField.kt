package frgp.utn.edu.ar.quepasa.presentation.ui.components.editables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergentField(
    onDismissRequest: () -> Unit,
    onRequest: () -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    title: String = "Modificar",
    confirmText: String = "Actualizar",
    content: @Composable () -> Unit
) {
    val co = rememberCoroutineScope()
    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 12.dp)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Row(modifier = rowModifier) {
            Text(
                text = title,
                textAlign = Center,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Row(modifier = rowModifier) {
            content()
        }
        Row(modifier = rowModifier, horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    co.launch { onRequest() }
                    onDismissRequest()
                },
                enabled = isValid
            ) {
                Text(confirmText)
            }
        }
    }
}