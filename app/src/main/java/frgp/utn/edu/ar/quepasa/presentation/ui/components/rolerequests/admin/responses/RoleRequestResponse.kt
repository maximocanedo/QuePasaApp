package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.admin.responses

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

fun onUpdateRequest(
    context: Context,
    navController: NavHostController,
    viewModel: RoleUpdateRequestViewModel,
    requestId: UUID,
    requestApprove: Boolean,
    requestRemarks: String
) {
    CoroutineScope(IO).launch {
        val result = viewModel.respondToRoleRequest(requestId, requestApprove, requestRemarks)

        withContext(Dispatchers.Main) {
            val response = if(requestApprove) "aprobada" else "cancelada"
            if(result) {
                Toast.makeText(context, "Solicitud $response", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Solicitud no $response (error)", Toast.LENGTH_SHORT).show()
            }
            navController.navigate("roleRequestAdminList")
        }
    }
}

fun onDeleteRequest(
    context: Context,
    navController: NavHostController,
    viewModel: RoleUpdateRequestViewModel,
    requestId: UUID
) {
    CoroutineScope(IO).launch {
        val result = viewModel.deleteRoleRequest(requestId, false)

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