package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.validators.commons.StringValidator

@Composable
fun TitleField(
    modifier: Modifier,
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
        label = "Título"
    )
}

@Preview
@Composable
fun TitleFieldPreview() {
    var title by remember { mutableStateOf("") }
    TitleField(
        modifier = Modifier,
        validator = { StringValidator(it) },
        onChange = { value, valid -> title = value },
        value = title
    )
}