package frgp.utn.edu.ar.quepasa.presentation.viewmodel.request

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.request.RoleUpdateRequest
import frgp.utn.edu.ar.quepasa.domain.repository.request.RoleUpdateRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RoleUpdateRequestViewModel @Inject constructor(
    private val repository: RoleUpdateRequestRepository
): ViewModel() {
    private val _roleRequests = MutableStateFlow<List<RoleUpdateRequest>>(emptyList())
    val roleRequests: StateFlow<List<RoleUpdateRequest>> get() = _roleRequests

    private val _roleRequest = MutableStateFlow(RoleUpdateRequest(null, null, null, null, "", null, RequestStatus.WAITING, false))
    val roleRequest: StateFlow<RoleUpdateRequest> get() = _roleRequest

    private val _fieldsValidation: List<MutableStateFlow<Boolean>> = List(2) { MutableStateFlow(false) }

    private val _errorMessage = MutableStateFlow<String?>(null)

    init {
        toggleValidationField(0, false)
        toggleValidationField(1, false)
    }

    suspend fun getRequests() {
        try {
            val requests = repository.getRequests()
            _roleRequests.value = requests
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getMyRequests() {
        try {
            val requests = repository.getMyRequests()
            _roleRequests.value = requests
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun createRoleRequest(role: Role, remarks: String): Boolean {
        try {
            val newRequest = repository.createRoleRequest(role, remarks)
            _roleRequest.value = newRequest
            return true
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
            return false
        }
    }

    suspend fun respondToRoleRequest(id: UUID, approve: Boolean, reviewerRemarks: String) {
        try {
            val newRequest = repository.respondToRoleRequest(id, approve, reviewerRemarks)
            _roleRequest.value = newRequest
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deleteRoleRequest(id: UUID, isRequester: Boolean) {
        try {
            repository.deleteRoleRequest(id)
            if(isRequester) {
                getMyRequests()
            }
            else {
                getRequests()
            }
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun toggleValidationField(index: Int, state: Boolean) {
        require(index in _fieldsValidation.indices) { "Index out of bounds" }
        _fieldsValidation[index].value = state
    }

    fun checkValidationFields(): Boolean {
        return _fieldsValidation.all { it.value }
    }
}