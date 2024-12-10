package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.user

import BaseComponent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.admin.responses.onDeleteRequest
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.WarningMessage
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.user.requests.RoleRequestCards
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel

@Composable
fun RoleUserListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val viewModel: RoleUpdateRequestViewModel = hiltViewModel()

    val requests = viewModel.roleRequests.collectAsState()

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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(!enabled) {
                if(enabledHighestRole) {
                    WarningMessage("Tenés una solicitud de rol pendiente. Esperá a que sea revisada antes de solicitar otra.")
                }
                else {
                    WarningMessage("Tenés el rol solicitable más alto. No es posible solicitar otro rol por el momento.")
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Button(
                modifier = Modifier,
                enabled = enabled,
                onClick = {
                    navController.navigate("roleRequest")
                }
            ) {
                Text("Solicitar rol")
            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            RoleRequestCards(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "En espera",
                status = RequestStatus.WAITING,
                requests = requests.value,
                hasDeleteButton = true,
                onDeleteRequest = { id ->
                    onDeleteRequest(
                        context = context,
                        navController = navController,
                        viewModel = viewModel,
                        requestId = id
                    )
                }
            )

            RoleRequestCards(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Aprobadas",
                status = RequestStatus.APPROVED,
                requests = requests.value,
                hasDeleteButton = false,
                onDeleteRequest = { id ->
                    onDeleteRequest(
                        context = context,
                        navController = navController,
                        viewModel = viewModel,
                        requestId = id
                    )
                }
            )

            RoleRequestCards(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Rechazadas",
                status = RequestStatus.REJECTED,
                requests = requests.value,
                hasDeleteButton = false,
                onDeleteRequest = { id ->
                    onDeleteRequest(
                        context = context,
                        navController = navController,
                        viewModel = viewModel,
                        requestId = id
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun RoleUpdateUserListScreenPreview() {
    val navController = rememberNavController()
    RoleUserListScreen(navController = navController)
}
