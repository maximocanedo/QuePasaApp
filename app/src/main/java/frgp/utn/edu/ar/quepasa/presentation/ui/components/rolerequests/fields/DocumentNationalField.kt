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
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.commons.StringValidator

@Composable
fun DocumentNationalField(
    modifier: Modifier,
    value: String,
    validator: (String) -> StringValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var content: String by remember { mutableStateOf(value) }
    var isValid: Boolean by remember { mutableStateOf(true) }
    var error: String by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier,
        value = content,
        onValueChange = {
            content = it
            var status = false
            try {
                validator(it).build()
                error = ""
                status = true
            }
            catch(err: ValidationError) {
                error = err.errors.first()
            }
            isValid = status
            onValidityChange(status)
            onChange(it)
        },
        isError = !isValid,
        label = { Text("DNI") },
        supportingText = { Text(error) }
    )
}

@Preview
@Composable
fun DocumentNationalFieldPreview() {
    var dni by remember { mutableStateOf("") }
    DocumentNationalField(
        modifier = Modifier,
        value = dni,
        validator = { StringValidator(dni) },
        onChange = { dni = it},
        onValidityChange = {}
    )
}