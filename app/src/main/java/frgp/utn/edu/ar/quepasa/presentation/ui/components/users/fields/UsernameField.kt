package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.users.UsernameValidator

@Composable
fun UsernameField(
    modifier: Modifier,
    value: String = "",
    validator: (String) -> UsernameValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var content: String by remember { mutableStateOf(value) }
    var isValid: Boolean by remember { mutableStateOf(true) };
    var error: String by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = content,
        onValueChange = {
            content = it
            CoroutineScope(IO).launch {
                var status = false
                try {
                    validator(it).build()
                    error = ""
                    status = true
                } catch(err: ValidationError) {
                    error = err.errors.first()
                }
                isValid = status
                onValidityChange(status)
                onChange(it)
            }
        },
        isError = !isValid,
        label = { Text("Nombre de usuario") },
        supportingText = { Text(error) },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false
        )
    )

}

@Preview
@Composable
fun UsernameFieldPreview() {
    var username by remember { mutableStateOf("") }
    UsernameField(
        modifier = Modifier,
        validator = {
            UsernameValidator(it)
                .meetsMaximumLength()
                .meetsMinimumLength()
                .neitherStartsNorEndsWithDoubleDotsOrUnderscores()
                .doesntHaveIllegalCharacters()
                .doesntHaveTwoDotsOrUnderscoresInARow()
        },
        onChange = { username = it },
        onValidityChange = { },
        value = username
    )
}
