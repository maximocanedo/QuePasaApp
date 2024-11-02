package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent

@Composable
fun UserProfile(navController: NavHostController, user: User?) { // TODO: Change to User (non-nullable) after login is implemented
    BaseComponent(navController, user, "Usuario") {

    }
}