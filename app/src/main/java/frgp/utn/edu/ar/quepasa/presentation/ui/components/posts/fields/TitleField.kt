package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.utils.validators.posts.TitleValidator

@Composable
fun TitleField(
    modifier: Modifier,
    value: String,
    validator: (String) -> TitleValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var isValid: Boolean by remember { mutableStateOf(true) }
    var error: String by remember { mutableStateOf("") }
    TextField(
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
        placeholder = { Text("Título")},
        supportingText = { Text(error) }
    )
}

@Preview
@Composable
fun TitleFieldPreview() {
    var title by remember { mutableStateOf("") }
    TitleField(
        modifier = Modifier,
        validator = {
            TitleValidator("title")
                .notEmpty()
                .meetsMinimumLength()
                .meetsMaximumLength()
        },
        onChange = { title = it },
        onValidityChange = {

        },
        value = title
    )
}