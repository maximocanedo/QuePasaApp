package frgp.utn.edu.ar.quepasa.presentation.viewmodel.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.request.RoleUpdateRequest
import frgp.utn.edu.ar.quepasa.domain.repository.request.RoleUpdateRequestRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RoleUpdateRequestViewModel @Inject constructor(
    private val repository: RoleUpdateRequestRepository
): ViewModel() {
    private val _roleRequests = MutableStateFlow<List<RoleUpdateRequest>>(emptyList())

    private val _roleRequest = MutableStateFlow(RoleUpdateRequest(null, null, null, null, "", null, RequestStatus.WAITING, false))

    private val _fieldValidation = MutableSharedFlow<Boolean>()

    private val _errorMessage = MutableStateFlow<String?>(null)

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

    suspend fun createRoleRequest(role: Role, remarks: String) {
        try {
            val newRequest = repository.createRoleRequest(role, remarks)
            _roleRequest.value = newRequest
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
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

    fun toggleValidationField(state: Boolean) {
        viewModelScope.launch {
            _fieldValidation.emit(state)
        }
    }

    suspend fun checkValidationField(): Boolean {
        var isValid = false
        _fieldValidation.collectLatest { value ->
            isValid = value
        }
        return isValid
    }
}