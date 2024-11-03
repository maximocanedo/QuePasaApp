package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import frgp.utn.edu.ar.quepasa.utils.validators.posts.DescriptionValidator

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DescriptionField(
    modifier: Modifier,
    value: String,
    validator: (String) -> DescriptionValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var isValid: Boolean by remember { mutableStateOf(true) }
    var error: String by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val status = validator(it).build()
            isValid = status.build().isValid()
            onValidityChange(isValid)
            if(status.getErrors().isNotEmpty())
                error = status.build().getErrors().first()
            else error = ""
            onChange(it)
        },
        isError = !isValid,
        label = { Text("Descripción") },
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
            DescriptionValidator("description")
                .notEmpty()
                .meetsMinimumLength()
                .meetsMaximumLength()
        },
        onChange = { description = it },
        onValidityChange = {

        },
        value = description
    )
}