package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent

@Composable
fun MainPage(navController: NavHostController, user: User?) { // TODO: Change to User (non-nullable) after login is implemented
    BaseComponent(navController, user, "¿Qué pasa?") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(BorderStroke(2.dp, Color.LightGray))
                .background(Color.White)
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Text(text = "Bienvenido, ${user?.name ?: "Usuario"}")
            }

            Button(
                onClick = { navController.navigate("trends") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Trends test")
            }
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { navController.navigate("postCreate") } ) {
                    Icon(
                        modifier = Modifier
                            .size(150.dp),
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Post Create",
                        tint = Color.White
                    )
                }
            }
        }
    }
}




