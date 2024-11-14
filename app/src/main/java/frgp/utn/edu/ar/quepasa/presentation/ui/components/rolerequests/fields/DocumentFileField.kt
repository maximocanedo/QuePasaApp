package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import frgp.utn.edu.ar.quepasa.R

@Composable
fun DocumentFileField(
    modifier: Modifier,
    value: String,
    onIconClick: () -> Unit
) {
    var content: String by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier,
        enabled = false,
        value = value,
        onValueChange = {
            content = it
        },
        label = { Text("Seleccione un archivo") },
        placeholder = { Text("Seleccione un archivo") },
        trailingIcon = {
            IconButton(onClick = onIconClick) {
                Icon(
                    painter = painterResource(R.drawable.baseline_attach_file_24),
                    contentDescription = "Select File Icon"
                )
            }
        }
    )
}