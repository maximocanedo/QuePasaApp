package frgp.utn.edu.ar.quepasa.data.source.remote.request

import frgp.utn.edu.ar.quepasa.data.model.request.RoleUpdateRequest
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface RoleUpdateRequestService {
    @POST("request/role/request")
    suspend fun createRoleRequest(@Query("requestedRole") requestedRole: String, @Query("remarks") remarks: String): Response<RoleUpdateRequest>

    @POST("request/role/respond")
    suspend fun respondToRoleRequest(@Query("requestId") requestId: UUID, @Query("approve") approve: Boolean, @Query("reviewerRemarks") reviewerRemarks: String): Response<RoleUpdateRequest>

    @GET("request/role/all")
    suspend fun getAllRequests(): Response<List<RoleUpdateRequest>>

    @GET("request/role/my-requests")
    suspend fun getMyRequests(): Response<List<RoleUpdateRequest>>

    @DELETE("request/role/{requestId}")
    suspend fun deleteRoleRequest(@Path("requestId") requestId: UUID): Response<Void>
}