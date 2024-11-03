package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.users.NameValidator


@Composable
fun NameField(
    modifier: Modifier,
    value: String,
    validator: (String) -> NameValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit,
    serverError: String?,
    clearServerError: () -> Unit
) {
    var isValid: Boolean by remember { mutableStateOf(serverError == null) };
    var error: String by remember { mutableStateOf(serverError?: "") }
    var c: String by remember { mutableStateOf(value) }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            c = it
            clearServerError()
            var status = false
            var content = ""
            try {
                content = validator(it).build()
                status = true
                error = ""
            } catch(err: ValidationError) {
                error = err.errors.first()
            }
            isValid = status
            onValidityChange(status)
            onChange(content)
        },
        isError = !isValid || serverError != null,
        label = { Text("Nombre") },
        supportingText = { Text(serverError?:error) }
    )

}

@Preview
@Composable
fun NameFieldPreview() {
    var name by remember { mutableStateOf("") }
    NameField(
        modifier = Modifier,
        validator = {
            NameValidator(it)
                .validateCompoundNames()
        },
        onChange = { name = it },
        onValidityChange = {

        },
        value = name,
        serverError = null,
        clearServerError = {}
    )
}