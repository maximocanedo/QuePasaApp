package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests

import BaseComponent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card.RoleUpdateRequestCards
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.WarningMessage
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel

@Composable
fun RoleUpdateUserListScreen(navController: NavHostController) {
    val viewModel: RoleUpdateRequestViewModel = hiltViewModel()
    val user by LocalAuth.current.collectAsState()
    var enabled by remember { mutableStateOf(true) }
    var enabledHighestRole by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getMyRequests()

        val role = user.user?.role
        enabled = !viewModel.checkForRoleRequestPending() && role != Role.GOVT
        enabledHighestRole = role != Role.GOVT
    }

    BaseComponent(navController, "Solicitudes de rol", false, "roleRequestUserList") {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            item {
                if (!enabled) {
                    if(enabledHighestRole) {
                        WarningMessage("Tienes una solicitud de rol pendiente. Espera a que sea revisada antes de solicitar otra.")
                    }
                    else {
                        WarningMessage("Tienes el rol solicitable más alto. No es posible solicitar otro rol por el momento.")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                Button(
                    modifier = Modifier,
                    enabled = enabled,
                    onClick = {
                        navController.navigate("roleRequest")
                    }
                ) {
                    Text("Solicitar rol")
                }
            }

            item {
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }

            item {
                Text(
                    text = "En espera",
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 64.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            item {
                RoleUpdateRequestCards(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    viewModel = viewModel,
                    navController = navController,
                    status = RequestStatus.WAITING,
                    isAdmin = false,
                    hasDeleteButton = true
                )
            }

            item {
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }

            item {
                Text(
                    text = "Aprobadas",
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 64.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            item {
                RoleUpdateRequestCards(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    viewModel = viewModel,
                    navController = navController,
                    status = RequestStatus.APPROVED,
                    isAdmin = false,
                    hasDeleteButton = false
                )
            }

            item {
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }

            item {
                Text(
                    text = "Rechazadas",
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 64.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            item {
                RoleUpdateRequestCards(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    viewModel = viewModel,
                    navController = navController,
                    status = RequestStatus.REJECTED,
                    isAdmin = false,
                    hasDeleteButton = false
                )
            }
        }
    }
}

@Preview
@Composable
fun RoleUpdateUserListScreenPreview() {
    val navController = rememberNavController()
    RoleUpdateUserListScreen(navController = navController)
}
