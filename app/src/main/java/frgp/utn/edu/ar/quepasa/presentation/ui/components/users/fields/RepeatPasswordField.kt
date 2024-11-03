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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.users.PasswordValidator


@Composable
fun RepeatPasswordField(
    modifier: Modifier,
    value: String,
    validator: (String) -> PasswordValidator,
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
        value = c,
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
        isError = !isValid,
        label = { Text("Repita la contraseña") },
        supportingText = { Text(serverError?:error) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

}

@Preview
@Composable
fun RepeatPasswordFieldPreview() {
    var initialPassword by remember { mutableStateOf("Hallo") }
    var password by remember { mutableStateOf("") }
    RepeatPasswordField(
        modifier = Modifier,
        validator = {
            PasswordValidator(it)
                .ifNullThen("")
                .equals(initialPassword)
        },
        onChange = { password = it },
        onValidityChange = {

        },
        value = password,
        serverError = null,
        clearServerError = {}
    )
}