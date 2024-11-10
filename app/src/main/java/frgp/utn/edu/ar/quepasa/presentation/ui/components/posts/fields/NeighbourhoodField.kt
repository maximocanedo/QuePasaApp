package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.neighbourhood.NeighbourhoodViewModel

@Composable
fun NeighbourhoodField(
    modifier: Modifier,
    audience: String,
    onItemSelected: (Long) -> Unit
) {
    val viewModel: NeighbourhoodViewModel = hiltViewModel()
    val neighbourhoods by viewModel.neighbourhoods.collectAsState()
    val items = neighbourhoods.map { it.name }
    val itemsId = neighbourhoods.map { it.id }

    if(items.isNotEmpty()) {
        val maxLength = 8
        var selectedItem by remember { mutableStateOf(items.firstOrNull() ?: "") }
        var expanded by remember { mutableStateOf(false) }
        var enabled by remember { mutableStateOf(false) }
        val fontSize = if (selectedItem.length > maxLength) 10.sp else 16.sp

        LaunchedEffect(audience) {
            enabled = audience == Audience.NEIGHBORHOOD.name
        }
        LaunchedEffect(enabled) {
            selectedItem = if (enabled) items.firstOrNull() ?: "" else ""
        }

        Box(modifier = modifier) {
            Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = selectedItem,
                    onValueChange = {},
                    textStyle = TextStyle(fontSize = fontSize),
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        var arrowIcon: ImageVector =
                            if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown
                        if (!enabled) arrowIcon = Icons.Filled.Clear
                        Icon(
                            imageVector = arrowIcon,
                            contentDescription = null,
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                )

                DropdownMenu(
                    expanded = expanded && enabled,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedItem = item
                                onItemSelected(itemsId[index])
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
    else {
        Text("Sin barrios", modifier = modifier)
    }
}

@Preview
@Composable
fun NeighbourhoodFieldPreview() {
    val audience by remember { mutableStateOf("") }
    var neighbourhood by remember { mutableLongStateOf(1) }
    NeighbourhoodField(
        modifier = Modifier,
        audience = audience,
        onItemSelected = { neighbourhood = it }
    )
}