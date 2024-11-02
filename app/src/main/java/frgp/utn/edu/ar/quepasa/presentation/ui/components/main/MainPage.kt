package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User

@Composable
fun MainPage(navController: NavHostController, user: User?) { // TODO: Change to User (non-nullable) after login is implemented
    Scaffold(
        topBar = { NavigationMainDrawer(navController, user, "¿Qué pasa?") }
    ) {

    }
}