package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.utils.validators.users.UsernameValidator

@Composable
fun UsernameField(
    modifier: Modifier,
    value: String,
    validator: (String) -> UsernameValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var isValid: Boolean by remember { mutableStateOf(true) };
    var error: String by remember { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val status = validator(it).build()
            isValid = status.build().isValid()
            onValidityChange(isValid)
            if(status.getErrors().count() > 0)
                error = status.build().getErrors().first()
            else error = ""
            onChange(it)
        },
        isError = !isValid,
        label = { Text("Nombre de usuario") },
        supportingText = { Text(error) }
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
        onValidityChange = {

        },
        value = username
    )
}