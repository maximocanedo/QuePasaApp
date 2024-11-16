package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostScreen

@Composable
fun MainPage(navController: NavHostController) { // TODO: Change to User (non-nullable) after login is implemented
    val user by LocalAuth.current.collectAsState()
    BaseComponent(navController, user.user, "¿Qué pasa?", false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)
                .border(BorderStroke(2.dp, Color.LightGray))
                .background(Color.White)
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Text(text = "Bienvenido, ${if (user.ok) user.name else "Usuario"}")
            }

            PostScreen(navController, selectedTag = null)
        }

        Column {
            val role: Role? = user.user?.role
            if (role != null && role != Role.USER) {
                Spacer(modifier = Modifier.weight(1f))
                CreateContentDropdown(navController = navController)
            }
        }
    }
}
