package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
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
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToEnglish

@Composable
fun DropdownAudienceField(
    modifier: Modifier,
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit,
    label: String = "",
) {
    var expanded by remember { mutableStateOf(false) }

    val maxLength = 10
    val fontSize = if (selectedItem.toString().length > maxLength) 12.sp else 16.sp

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedItem.toString(),
            onValueChange = {},
            textStyle = TextStyle(fontSize = fontSize),
            label = { Text(label) },
            placeholder = { Text(label) },
            readOnly = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            shape = RoundedCornerShape(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(audienceToEnglish(item).name)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownAudienceFieldPreview() {
    DropdownAudienceField(
        modifier = Modifier,
        items = emptyList(),
        selectedItem = "",
        onItemSelected = {},
        label = "Audiencias"
    )
}