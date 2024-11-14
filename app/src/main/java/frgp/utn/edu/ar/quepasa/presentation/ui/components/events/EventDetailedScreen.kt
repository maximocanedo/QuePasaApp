package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import java.util.UUID

@Composable
fun EventDetailedScreen(navController: NavHostController, user: User?, eventId: UUID) {
    val context = LocalContext.current
    val viewModel: EventViewModel = hiltViewModel()

    val event by viewModel.event.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEventById(eventId)
    }

    if (event != null) {
        BaseComponent(
            navController = navController,
            user = user,
            title = "Detalle Evento",
            back = true
        ) {
            Column() {
                Row {
                    event!!.title?.let { Text(text = it, fontSize = 24.sp) }
                }
            }
        }
    }
}