package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.foundation.text.KeyboardOptions
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
import frgp.utn.edu.ar.quepasa.utils.validators.users.PasswordValidator


@Composable
fun SignupPasswordField(
    modifier: Modifier,
    value: String,
    validator: (String) -> PasswordValidator,
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
        label = { Text("Contraseña") },
        supportingText = { Text(error) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

}

@Preview
@Composable
fun SignupPasswordFieldPreview() {
    var password by remember { mutableStateOf("") }
    SignupPasswordField(
        modifier = Modifier,
        validator = {
            PasswordValidator(it)
                .hasOneDigit()
                .hasOneLowerCaseLetter()
                .hasOneUpperCaseLetter()
                .hasOneSpecialCharacter()
                .lengthIsEightCharactersOrMore()
        },
        onChange = { password = it },
        onValidityChange = {

        },
        value = password
    )
}