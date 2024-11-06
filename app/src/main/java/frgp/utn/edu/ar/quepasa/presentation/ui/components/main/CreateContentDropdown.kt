package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CreateContentDropdown(navController : NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Post/Event Create",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = (-10).dp, y = 0.dp)
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