package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience

@Composable
fun AudienceField(modifier: Modifier, onItemSelected: (String) -> Unit) {
    val audiences: List<Audience> = Audience.entries

    var selectedItem by remember { mutableStateOf(audiences.firstOrNull()) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector =  Icons.Filled.Person,
                contentDescription = null,
            )

            OutlinedTextField(
                value = selectedItem.toString(),
                onValueChange = {},
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
                audiences.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },
                        onClick = {
                            selectedItem = item
                            onItemSelected(item.toString())
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}