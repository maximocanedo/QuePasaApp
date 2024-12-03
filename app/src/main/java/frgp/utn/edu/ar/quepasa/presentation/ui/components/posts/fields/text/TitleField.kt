package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.text

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.validators.commons.StringValidator

@Composable
fun TitleField(
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
        label = "Título",
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
fun TitleFieldPreview() {
    TitleField(
        modifier = Modifier,
        value = "",
        validator = { value -> StringValidator(value).isNotBlank() },
        onChange = { value, change -> println("Value: $value, onChange: $change")}
    )
}