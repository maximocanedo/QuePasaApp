package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.validators.users.MailValidator


@Composable
fun MailField(
    modifier: Modifier = Modifier,
    value: String = "",
    validator: (String) -> MailValidator,
    onChange: (String, Boolean) -> Unit,
    serverError: String? = null,
    clearServerError: () -> Unit = {}
) {
    OutlinedField<MailValidator, String>(
        modifier = modifier,
        validator = validator,
        valueConverter = { it },
        value = value,
        textConverter = { it },
        clearServerError = clearServerError,
        onChange = onChange,
        serverError = serverError,
        label = "Dirección de correo electrónico"
    )
}


@Preview
@Composable
fun MailFieldPreview() {
    var mail by remember { mutableStateOf("") }
    MailField(
        modifier = Modifier,
        validator = {
            MailValidator(it)
            .ifNullOrBlankThen("")
            .isNotBlank()
            .isValidAddress()
        },
        onChange = { value, valid -> mail = value },
        value = mail
    )
}