package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent

@Composable
fun EventDetailedScreen(navController: NavHostController, user: User?) {
    val context = LocalContext.current

    BaseComponent(
        navController = navController,
        user = user,
        title = "Detalle Evento",
        back = true
    ) {

    }

}