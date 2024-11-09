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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToEnglish
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToSpanish
import frgp.utn.edu.ar.quepasa.utils.audience.audiencesToSpanish

@Composable
fun AudienceField(
    modifier: Modifier,
    audience: String,
    onItemSelected: (String) -> Unit) {
    val audiences: List<String> = audiencesToSpanish()

    val maxLength = 8
    var selectedItem by remember { mutableStateOf("") }
    if(audience.isNotBlank()) {
        selectedItem = audiences.find { item -> audienceToSpanish(audience) == item }.toString()
    }
    else {
        audiences.firstOrNull() ?: ""
    }
    var expanded by remember { mutableStateOf(false) }
    val fontSize = if (selectedItem.length > maxLength) 12.sp else 16.sp

    Box(modifier = modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = selectedItem,
                onValueChange = {},
                textStyle = TextStyle(fontSize = fontSize),
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
                    .clickable { expanded = !expanded }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                audiences.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            onItemSelected(audienceToEnglish(item).name)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}