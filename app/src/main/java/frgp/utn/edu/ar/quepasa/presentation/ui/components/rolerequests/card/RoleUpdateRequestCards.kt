package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel

@Composable
fun RoleUpdateRequestCards(
    modifier: Modifier,
    viewModel: RoleUpdateRequestViewModel,
    status: RequestStatus,
    isAdmin: Boolean,
    hasDeleteButton: Boolean,
) {
    val requests by viewModel.roleRequests.collectAsState()

    if(requests.isNotEmpty()) {
        var count = 0
        requests.forEach { request ->
            if(request.status == status) {
                if(!isAdmin) {
                    RoleUpdateRequestCardUser(request, hasDeleteButton)
                }
                else {
                    RoleUpdateRequestCardAdmin(request)
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
    RoleUpdateRequestCards(modifier = Modifier, viewModel = viewModel, status = RequestStatus.WAITING, false, false)
}