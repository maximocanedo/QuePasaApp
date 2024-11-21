package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.tags

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.validators.commons.StringValidator

@Composable
fun TagField(
    modifier: Modifier = Modifier,
    value: String = "",
    validator: (String) -> StringValidator,
    onChange: (String, Boolean) -> Unit,
    onAdd: (String) -> Unit,
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
        label = "Etiquetas",
        trailingIcon = {
            IconButton(onClick = {
                if(value.isNotBlank()) {
                    println("tag $value")
                    onAdd(value)
                }
                else {
                    // TODO: Server error logic
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Tag",
                )
            }
        }
    )
}

@Preview
@Composable
fun DescriptionFieldPreview() {
    TagField(
        modifier = Modifier,
        value = "",
        validator = { value -> StringValidator(value).isNotBlank() },
        onChange = { value, change -> println("Value: $value, onChange: $change")},
        onAdd = {}
    )
}