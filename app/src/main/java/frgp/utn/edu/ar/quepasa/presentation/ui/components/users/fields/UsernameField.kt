package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.users.UsernameValidator

@Composable
fun UsernameField(
    modifier: Modifier = Modifier,
    value: String = "",
    validator: (String) -> UsernameValidator = { UsernameValidator(it) },
    onChange: (String, Boolean) -> Unit,
    serverError: String? = null,
    clearServerError: () -> Unit = {}
) {

    OutlinedField<UsernameValidator, String>(
        modifier = modifier,
        validator = validator,
        valueConverter = { it },
        value = value,
        textConverter = { it },
        clearServerError = clearServerError,
        onChange = onChange,
        serverError = serverError,
        label = "Nombre de usuario"
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
        onChange = { value: String, valid: Boolean ->
            username = value
        },
        value = username
    )
}
