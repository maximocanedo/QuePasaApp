package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NameField(
    modifier: Modifier,
    value: String,
) {
    var content: String by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier,
        enabled = false,
        value = value,
        onValueChange = {
            content = it
        },
        label = { Text("Nombre") },
    )
}

@Preview
@Composable
fun NameFieldPreview() {
    val name by remember { mutableStateOf("") }
    NameField(
        modifier = Modifier,
        value = name,
    )
}