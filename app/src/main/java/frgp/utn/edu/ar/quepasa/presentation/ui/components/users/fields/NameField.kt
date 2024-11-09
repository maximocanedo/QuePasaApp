package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.commons.StringValidator
import quepasa.api.validators.users.UsernameValidator


@Composable
fun NameField(
    modifier: Modifier = Modifier,
    value: String = "",
    validator: (String) -> StringValidator,
    onChange: (String, Boolean) -> Unit,
    serverError: String? = null,
    clearServerError: () -> Unit = {}
) {
    OutlinedField<StringValidator, String>(
        modifier = modifier,
        validator = validator,
        valueConverter = { it },
        value = value,
        textConverter = { it },
        clearServerError = clearServerError,
        onChange = onChange,
        serverError = serverError,
        label = "Nombre"
    )
}


@Preview
@Composable
fun NameFieldPreview() {
    var name by remember { mutableStateOf("") }
    NameField(
        modifier = Modifier,
        validator = { StringValidator(it) },
        onChange = { value, valid -> name = value },
        value = name
    )
}