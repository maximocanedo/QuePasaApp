package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RoleUpdateRequestCards(
    modifier: Modifier,
    viewModel: RoleUpdateRequestViewModel,
    navController: NavHostController,
    status: RequestStatus,
    isAdmin: Boolean,
    hasDeleteButton: Boolean,
) {
    val context = LocalContext.current
    val requests by viewModel.roleRequests.collectAsState()

    if(requests.isNotEmpty()) {
        var count = 0
        requests.forEach { request ->
            if(request.status == status) {
                if(!isAdmin) {
                    RoleUpdateRequestCardUser(
                        request = request,
                        hasDeleteButton = hasDeleteButton,
                        onUpdateRequest = { id ->
                            CoroutineScope(IO).launch {
                                val result = viewModel.deleteRoleRequest(id, true)

                                withContext(Dispatchers.Main) {
                                    if(result) {
                                        Toast.makeText(context, "Solicitud cancelada", Toast.LENGTH_SHORT).show()
                                    }
                                    else {
                                        Toast.makeText(context, "Solicitud no cancelada (error)", Toast.LENGTH_SHORT).show()
                                    }
                                    navController.navigate("roleRequestUserList")
                                }
                            }
                        }
                    )
                }
                else {
                    RoleUpdateRequestCardAdmin(
                        request = request,
                        onUpdateRequest = { id, approve, remark ->
                            CoroutineScope(IO).launch {
                                val result = viewModel.respondToRoleRequest(id, approve, remark)

                                withContext(Dispatchers.Main) {
                                    val response = if(approve) "aprobada" else "cancelada"
                                    if(result) {
                                        Toast.makeText(context, "Solicitud $response", Toast.LENGTH_SHORT).show()
                                    }
                                    else {
                                        Toast.makeText(context, "Solicitud no $response (error)", Toast.LENGTH_SHORT).show()
                                    }
                                    navController.navigate("roleRequestAdminList")
                                }
                            }
                        },
                        onDeleteRequest = { id ->
                            CoroutineScope(IO).launch {
                                val result = viewModel.deleteRoleRequest(id, true)

                                withContext(Dispatchers.Main) {
                                    if(result) {
                                        Toast.makeText(context, "Solicitud eliminada", Toast.LENGTH_SHORT).show()
                                    }
                                    else {
                                        Toast.makeText(context, "Solicitud no eliminada (error)", Toast.LENGTH_SHORT).show()
                                    }
                                    navController.navigate("roleRequestAdminList")
                                }
                            }
                        }
                    )
                }
                count++
            }
        }
        if(count == 0) {
            Text(modifier = modifier, text = "Sin solicitudes.")
        }
    }
    else {
        Text(modifier = modifier, text = "Sin solicitudes.")
    }
}

@Preview
@Composable
fun RoleUpdateRequestCardsPreview() {
    val viewModel: RoleUpdateRequestViewModel = hiltViewModel()
    val navController = rememberNavController()
    RoleUpdateRequestCards(
        modifier = Modifier,
        viewModel = viewModel,
        navController = navController,
        status = RequestStatus.WAITING,
        isAdmin = false,
        hasDeleteButton = false)
}