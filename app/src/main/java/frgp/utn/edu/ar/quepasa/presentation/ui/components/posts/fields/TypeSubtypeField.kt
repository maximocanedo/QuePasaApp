package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostSubtypeViewModel

@Composable
fun TypeSubtypeField(modifier: Modifier, type: Int, onItemSelected: (Int) -> Unit) {
    val viewModel: PostSubtypeViewModel = hiltViewModel()

    LaunchedEffect(type) {
        viewModel.getSubtypesByType(type, 0, 10)
    }

    val postSubtypes by viewModel.postSubtypes.collectAsState()
    val items = postSubtypes.content.map { it.description ?: "" }

    if(items.isNotEmpty()) {
        val maxLength = 14
        var selectedItem by remember { mutableStateOf("") }
        val fontSize = if (selectedItem.length > maxLength) 14.sp else 16.sp
        LaunchedEffect(items) {
            selectedItem = items.firstOrNull() ?: ""
        }
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = modifier) {
            TextField(
                value = selectedItem,
                onValueChange = {},
                textStyle = TextStyle(fontSize = fontSize),
                label = { Text("Subtipo") },
                placeholder = { Text("Subtipo")},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            onItemSelected(item.toInt())
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    else {
        Spacer(modifier = modifier)
        Text("Sin subtipos", modifier = modifier)
    }
}

@Preview
@Composable
fun TypeSubtypeFieldPreview() {
    var subtype by remember { mutableIntStateOf(0) }
    val type by remember { mutableIntStateOf(0) }
    TypeSubtypeField(modifier = Modifier.fillMaxWidth(), type, onItemSelected = { subtype = it })
}