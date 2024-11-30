package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> DropdownPostField(
    modifier: Modifier,
    items: List<T>,
    itemsId: List<T> = emptyList(),
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    enabled: Boolean,
    label: String = "",
    leadIcon: (@Composable () -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    val maxLength = 10
    val fontSize = if (selectedItem.toString().length > maxLength) 12.sp else 16.sp

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedItem.toString(),
            enabled = enabled,
            onValueChange = {},
            textStyle = TextStyle(fontSize = fontSize),
            label = { Text(label) },
            placeholder = { Text(label) },
            readOnly = true,
            leadingIcon = leadIcon?.let { { it() } },
            trailingIcon = if(enabled) {
                {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        modifier = modifier.clickable { expanded = !expanded }
                    )
                }
            }
            else null,
            shape = RoundedCornerShape(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(item.toString()) },
                    onClick = {
                        if(itemsId.isEmpty()) {
                            onItemSelected(item)
                        }
                        else {
                            onItemSelected(itemsId[index])
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownPostFieldPreview() {
    DropdownPostField(
        modifier = Modifier,
        items = emptyList(),
        itemsId =  emptyList(),
        selectedItem = "",
        onItemSelected = {},
        enabled = true,
        label = "Placeholder"
    )
}