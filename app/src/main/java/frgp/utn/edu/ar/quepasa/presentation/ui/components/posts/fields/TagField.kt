package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.commons.StringValidator

@Composable
fun TagField(
    modifier: Modifier,
    value: String,
    validator: (String) -> StringValidator,
    onChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit,
    viewModel: PostViewModel
) {
    Column(modifier = modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            var content: String by remember { mutableStateOf(value) }
            var isValid: Boolean by remember { mutableStateOf(true) }
            var error: String by remember { mutableStateOf("") }
            TextField(
                modifier = modifier.weight(1f),
                value = content,
                onValueChange = {
                    content = it
                    CoroutineScope(IO).launch {
                        var status = false
                        try {
                            validator(it).build()
                            error = ""
                            status = true
                        }
                        catch(err: ValidationError) {
                            error = err.errors.first()
                        }
                        isValid = status
                        onValidityChange(status)
                        onChange(it)
                    }
                },
                isError = !isValid,
                placeholder = { Text("Etiquetas")},
                supportingText = { Text(error) }
            )

            IconButton(onClick = {
                if(content.isNotBlank()) {
                    viewModel.addTag(value)
                    content = ""
                }
            } ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Tag Add",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun TagFieldPreview() {
    val viewModel: PostViewModel = hiltViewModel()
    var tag by remember { mutableStateOf("") }
    TagField(
        modifier = Modifier,
        value = tag,
        validator =  {
            StringValidator(tag)
                .isNotBlank()
                .hasMaximumLength(15)
                .hasMinimumLength(4)
        },
        onChange = {
            newTags -> tag = newTags
        },
        onValidityChange = {},
        viewModel = viewModel
    )
}