package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.admin

import BaseComponent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.admin.requests.RoleRequestCards
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.admin.responses.onDeleteRequest
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.admin.responses.onUpdateRequest
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel

@Composable
fun RoleAdminListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val viewModel: RoleUpdateRequestViewModel = hiltViewModel()

    val requests = viewModel.roleRequests.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRequests()
    }

    BaseComponent(navController, "Solicitudes de rol", false, "roleRequestAdminList") {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoleRequestCards(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "En espera",
                status = RequestStatus.WAITING,
                requests = requests.value,
                onUpdateRequest = { id, approve, remarks ->
                    onUpdateRequest(
                        context = context,
                        navController = navController,
                        viewModel = viewModel,
                        requestId = id,
                        requestApprove = approve,
                        requestRemarks = remarks
                    )
                },
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
                onUpdateRequest = { id, approve, remarks ->
                    onUpdateRequest(
                        context = context,
                        navController = navController,
                        viewModel = viewModel,
                        requestId = id,
                        requestApprove = approve,
                        requestRemarks = remarks
                    )
                },
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
                onUpdateRequest = { id, approve, remarks ->
                    onUpdateRequest(
                        context = context,
                        navController = navController,
                        viewModel = viewModel,
                        requestId = id,
                        requestApprove = approve,
                        requestRemarks = remarks
                    )
                },
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
fun RoleUpdateAdminListScreenPreview() {
    val navController = rememberNavController()
    RoleAdminListScreen(navController = navController)
}