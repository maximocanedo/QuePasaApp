package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue1

@Composable
fun CreateContentDropdown(navController : NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        FloatingActionButton(
            onClick = { expanded = true },
            modifier = Modifier.padding(end = 60.dp, bottom = 10.dp),
            containerColor = Blue1,
        ) {
            Icon(
                Icons.Filled.Add,
                "Post/Event Create",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = (-40).dp, y = (-10).dp)
        ) {
            DropdownMenuItem(
                text = { Text("Publicación") },
                onClick = {
                    expanded = false
                    navController.navigate("postCreate")
                }
            )
            DropdownMenuItem(
                text = { Text("Evento") },
                onClick = {
                    expanded = false
                    navController.navigate("eventCreate")
                }
            )
        }
    }
}