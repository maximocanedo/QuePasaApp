package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card.RoleUpdateRequestCards
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel

@Composable
fun RoleUpdateAdminListScreen(navController: NavHostController) {
    val viewModel: RoleUpdateRequestViewModel = hiltViewModel()
    val user by LocalAuth.current.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRequests()
    }

    BaseComponent(navController, null, "Solicitudes de rol", false) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "En espera",
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 64.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
            RoleUpdateRequestCards(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                viewModel = viewModel,
                status = RequestStatus.WAITING
            )

            Spacer(modifier = Modifier.padding(vertical = 32.dp))

            Text(
                text = "Aprobadas",
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 64.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
            RoleUpdateRequestCards(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                viewModel = viewModel,
                status = RequestStatus.APPROVED
            )

            Spacer(modifier = Modifier.padding(vertical = 32.dp))

            Text(
                text = "Rechazadas",
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 64.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
            RoleUpdateRequestCards(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                viewModel = viewModel,
                status = RequestStatus.REJECTED
            )
        }
    }
}

@Preview
@Composable
fun RoleUpdateAdminListScreenPreview() {
    val navController = rememberNavController()
    RoleUpdateAdminListScreen(navController = navController)
}