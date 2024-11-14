package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.data.model.request.RoleUpdateRequest
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel

@Composable
fun RoleUpdateRequestCards(
    modifier: Modifier,
    viewModel: RoleUpdateRequestViewModel,
    status: RequestStatus
) {
    var requests: List<RoleUpdateRequest> = emptyList()
    LaunchedEffect(Unit) {
        requests = viewModel.getRequestsByStatus(status)
    }
    if(requests.isNotEmpty()) {
        requests.forEach { request ->
            RoleUpdateRequestCard()
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
    RoleUpdateRequestCards(modifier = Modifier, viewModel = viewModel, status = RequestStatus.WAITING)
}