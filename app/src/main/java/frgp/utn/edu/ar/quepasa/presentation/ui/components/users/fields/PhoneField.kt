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
import quepasa.api.validators.users.PhoneValidator


@Composable
fun PhoneField(
    modifier: Modifier = Modifier,
    value: String = "",
    validator: (String) -> PhoneValidator = {
        PhoneValidator(it)
            .ifNullThen("")
            .isNotBlank()
            .isValidPhoneNumber()
    },
    onChange: (String, Boolean) -> Unit,
    serverError: String? = null,
    clearServerError: () -> Unit = {}
) {
    OutlinedField<PhoneValidator, String>(
        modifier = modifier,
        validator = validator,
        valueConverter = { it },
        value = value,
        textConverter = { it },
        clearServerError = clearServerError,
        onChange = onChange,
        serverError = serverError,
        label = "Número de teléfono"
    )
}


@Preview
@Composable
fun PhoneFieldPreview() {
    var mail by remember { mutableStateOf("") }
    PhoneField(
        modifier = Modifier,
        validator = {
            PhoneValidator(it)
            .ifNullOrBlankThen("")
            .isNotBlank()
                .isValidPhoneNumber()
        },
        onChange = { value, valid -> mail = value },
        value = mail
    )
}