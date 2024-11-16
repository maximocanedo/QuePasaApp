package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun CreateContentDropdown(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        FloatingActionButton(
            onClick = { expanded = true },
            containerColor = Blue1,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 1.dp, end = 16.dp)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Post/Event Create",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = (-20).dp, y = 48.dp)
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
