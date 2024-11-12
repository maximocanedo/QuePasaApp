package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields

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
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.utils.events.categoriesToSpanish
import frgp.utn.edu.ar.quepasa.utils.events.categoryToEnglish
import frgp.utn.edu.ar.quepasa.utils.events.categoryToSpanish

@Composable
fun EventCategoryField(
    modifier: Modifier,
    category: String,
    onItemSelected: (String) -> Unit
) {
    val categories: List<String> = categoriesToSpanish()
    var selectedItem by remember { mutableStateOf("") }
    if (category.isNotBlank()) {
        selectedItem = categories.find { item -> categoryToSpanish(category) == item }.toString()
    } else {
        categories.firstOrNull() ?: ""
    }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                label = { Text("Categoría") }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            onItemSelected(categoryToEnglish(item).name)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EventCategoryFieldPreview() {
    var audience by remember { mutableStateOf("") }
    EventAudienceField(
        modifier = Modifier,
        audience = audience,
        onItemSelected = { audience = it }
    )
}