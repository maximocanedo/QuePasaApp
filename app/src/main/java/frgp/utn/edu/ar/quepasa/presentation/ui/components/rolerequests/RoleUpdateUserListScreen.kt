package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card.RoleUpdateRequestCard

@Composable
fun RoleUpdateUserListScreen(navController: NavHostController) {
    BaseComponent(navController, null, "Solicitudes de rol", false) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Button(
                modifier = Modifier,
                onClick = {
                    navController.navigate("roleRequest")
                }
            ) {
                Text("Solicitar rol")
            }

            Text(
                text = "Solicitudes - En espera",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 6.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
            RoleUpdateRequestCard()

            Text(
                text = "Solicitudes - Aprobadas",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 6.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
            RoleUpdateRequestCard()

            Text(
                text = "Solicitudes - Rechazadas",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 6.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
            RoleUpdateRequestCard()
        }
    }
}
