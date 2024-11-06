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
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Composable
fun TagField(
    modifier: Modifier,
    value: String,
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
                            error = ""
                            status = true
                        }
                        catch(err: Exception) {
                            error = err.message.toString()
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