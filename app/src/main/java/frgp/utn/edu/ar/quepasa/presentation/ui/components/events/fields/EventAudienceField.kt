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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToEnglish
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToSpanish
import frgp.utn.edu.ar.quepasa.utils.audience.audiencesToSpanish

@Composable
fun EventAudienceField(
    modifier: Modifier,
    audience: String,
    onItemSelected: (String) -> Unit
) {
    val audiences: List<String> = audiencesToSpanish()
    var selectedItem by remember { mutableStateOf("") }
    if (audience.isNotBlank()) {
        selectedItem = audiences.find { item -> audienceToSpanish(audience) == item }.toString()
    } else {
        audiences.firstOrNull() ?: ""
    }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
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
                label = { Text("Audiencia") }
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

@Preview
@Composable
fun EventAudienceFieldPreview() {
    var audience by remember { mutableStateOf("") }
    EventAudienceField(
        modifier = Modifier,
        audience = audience,
        onItemSelected = { audience = it }
    )
}