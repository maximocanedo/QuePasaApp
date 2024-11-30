package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import frgp.utn.edu.ar.quepasa.data.model.PostType
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Deprecated("Usar DropdownPostField")
@Composable
fun TypeField(
    modifier: Modifier,
    types: List<PostType>,
    onItemSelected: (Int) -> Unit
) {
    val items = types.map { it.description }
    val itemsId = types.map { it.id }

    if(items.isNotEmpty()) {
        var selectedItem by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }

        LaunchedEffect(types) {
            selectedItem = items.firstOrNull() ?: ""
        }

        val maxLength = 8
        val fontSize = if (selectedItem.length > maxLength) 13.sp else 16.sp

        val borderColor = MaterialTheme.colorScheme.primary
        val backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)

        Box(modifier = modifier) {
            TextField(
                value = selectedItem,
                onValueChange = {},
                textStyle = TextStyle(fontSize = fontSize),
                label = { Text("Tipo") },
                placeholder = { Text("Tipo") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = modifier.clickable { expanded = !expanded }
                    )
                },
                modifier = modifier
                    .clickable { expanded = !expanded }
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        BorderStroke(1.dp, color = borderColor),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            onItemSelected(itemsId[index])
                            expanded = false
                        },
                        modifier = modifier.padding(8.dp)
                    )
                }
            }
        }
    }
    else {
        TextField(
            value = "",
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Preview
@Composable
fun TypeFieldPreview() {
    TypeField(modifier = Modifier.wrapContentWidth(), types = emptyList(), onItemSelected = {})
}