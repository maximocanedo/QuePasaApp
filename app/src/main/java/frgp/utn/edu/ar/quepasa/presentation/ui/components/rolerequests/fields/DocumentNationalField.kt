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
fun DocumentNationalField(
    modifier: Modifier,
    value: String,
    onChange: (String) -> Unit,
) {
    var content: String by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier,
        value = content,
        onValueChange = {
            content = it
            onChange(it)
        },
        label = { Text("DNI") },
    )
}

@Preview
@Composable
fun DocumentNationalFieldPreview() {
    var dni by remember { mutableStateOf("") }
    DocumentNationalField(
        modifier = Modifier,
        value = dni,
        onChange = { dni = it}
    )
}