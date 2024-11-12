package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.validators.events.EventAddressValidator

@Composable
fun AddressField(
    modifier: Modifier,
    value: String = "",
    validator: (String) -> EventAddressValidator = { EventAddressValidator(it) },
    onChange: (String, Boolean) -> Unit,
    serverError: String? = null,
    clearServerError: () -> Unit = {}
) {
    OutlinedField<EventAddressValidator, String>(
        modifier = modifier,
        validator = validator,
        valueConverter = { it },
        value = value,
        textConverter = { it },
        clearServerError = clearServerError,
        onChange = onChange,
        serverError = serverError,
        label = "Dirección"
    )
}

@Preview
@Composable
fun AddressFieldPreview() {
    var address by remember { mutableStateOf("") }
    AddressField(
        modifier = Modifier,
        validator = {
            EventAddressValidator(it)
                .isNotBlank()
                .meetsLimits()
        },
        onChange = { value, valid -> address = value },
        value = address
    )
}
