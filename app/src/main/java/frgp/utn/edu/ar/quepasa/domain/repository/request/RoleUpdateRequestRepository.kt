package frgp.utn.edu.ar.quepasa.domain.repository.request

import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.request.RoleUpdateRequest
import frgp.utn.edu.ar.quepasa.data.source.remote.request.RoleUpdateRequestService
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class RoleUpdateRequestRepository @Inject constructor(
    private val roleUpdateRequestService: RoleUpdateRequestService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T & Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.errorBody()}")
        }
    }

    suspend fun getRequests(): List<RoleUpdateRequest> =
        handleResponse { roleUpdateRequestService.getAllRequests() }

    suspend fun getMyRequests(): List<RoleUpdateRequest> =
        handleResponse { roleUpdateRequestService.getMyRequests() }

    suspend fun createRoleRequest(role: Role, remarks: String): RoleUpdateRequest =
        handleResponse { roleUpdateRequestService.createRoleRequest(role, remarks) }

    suspend fun respondToRoleRequest(id: UUID, approve: Boolean, reviewerRemarks: String): RoleUpdateRequest =
        handleResponse { roleUpdateRequestService.respondToRoleRequest(id, approve, reviewerRemarks) }

    suspend fun deleteRoleRequest(id: UUID) {
        handleResponse<Void> { roleUpdateRequestService.deleteRoleRequest(id) }
    }
}