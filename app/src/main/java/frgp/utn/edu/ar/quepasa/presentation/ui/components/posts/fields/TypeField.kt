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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostTypeViewModel

@Composable
fun TypeField(modifier: Modifier, onItemSelected: (Int) -> Unit) {
    val viewModel: PostTypeViewModel = hiltViewModel()
    val postTypes by viewModel.postTypes.collectAsState()
    val items = postTypes.content.map { it.description ?: "" }
    val itemsId = postTypes.content.map { it.id }

    if(items.isNotEmpty()) {
        var selectedItem by remember { mutableStateOf(items.firstOrNull() ?: "") }
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = modifier) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                label = { Text("Tipo") },
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
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            itemsId[index]?.let { onItemSelected(it) }
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    else {
        Spacer(modifier = modifier)
        Text("Sin tipos", modifier = modifier)
    }
}

@Preview
@Composable
fun TypeFieldPreview() {
    var type by remember { mutableIntStateOf(0) }
    TypeField(modifier = Modifier.fillMaxWidth(), onItemSelected = { type = it })
}