package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.commons.StringValidator

@Composable
fun DescriptionField(
    modifier: Modifier,
    value: String,
    validator: (String) -> StringValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var content: String by remember { mutableStateOf(value) }
    var isValid: Boolean by remember { mutableStateOf(true) }
    var error: String by remember { mutableStateOf("") }
    TextField(
        modifier = modifier.height(150.dp),
        value = value,
        onValueChange = {
            content = it
            CoroutineScope(IO).launch {
                var status = false
                try {
                    validator(it).build()
                    error = ""
                    status = true
                } catch(err: ValidationError) {
                    error = err.errors.first()
                }
                isValid = status
                onValidityChange(status)
                onChange(it)
            }
        },
        isError = !isValid,
        placeholder = { Text("¿Qué pasa?")},
        supportingText = { Text(error) }
    )
}

@Preview
@Composable
fun DescriptionFieldPreview() {
    var description by remember { mutableStateOf("") }
    DescriptionField(
        modifier = Modifier,
        validator = {
            StringValidator("description")
                .isNotBlank()
                .hasMinimumLength(3)
                .hasMaximumLength(256)
        },
        onChange = { description = it },
        onValidityChange = {

        },
        value = description
    )
}