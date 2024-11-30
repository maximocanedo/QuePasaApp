package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToEnglish

@Deprecated("Usar DropdownAudienceField")
@Composable
fun AudienceField(
    modifier: Modifier,
    audiences: List<String>,
    onItemSelected: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(audiences.firstOrNull() ?: "") }
    var expanded by remember { mutableStateOf(false) }

    val maxLength = 8
    val fontSize = if (selectedItem.length > maxLength) 12.sp else 16.sp

    val borderColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)

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
                    .clickable { expanded = !expanded }
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        BorderStroke(1.dp, color = borderColor),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
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
fun AudienceFieldPreview() {
    AudienceField(modifier = Modifier, audiences = emptyList(), onItemSelected = {})
}