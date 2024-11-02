package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent

@Composable
fun MainPage(navController: NavHostController, user: User?) { // TODO: Change to User (non-nullable) after login is implemented
    BaseComponent(navController, user, "¿Qué pasa?") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(BorderStroke(2.dp, Color.LightGray))
                .background(Color.White)
        ) {
            Text(text = "Test")
        }
    }
}