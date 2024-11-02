package frgp.utn.edu.ar.quepasa.presentation.ui.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.NavigationMainDrawer

@Composable
fun BaseComponent(
    navController: NavHostController,
    user: User?,
    title: String,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { NavigationMainDrawer(navController, user, title) }
    ) {

    }
}