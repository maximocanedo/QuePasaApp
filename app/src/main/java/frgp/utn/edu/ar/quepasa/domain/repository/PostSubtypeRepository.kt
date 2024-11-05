package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.request.PostSubtypeRequest
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.data.source.remote.PostSubtypeService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import javax.inject.Inject

class PostSubtypeRepository @Inject constructor(
    private val postSubtypeService: PostSubtypeService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }

    suspend fun getSubtypes(page: Int, size: Int, activeOnly: Boolean): Page<PostSubtype> =
        handleResponse { postSubtypeService.getSubtypes(page, size, activeOnly) }

    suspend fun getSubtypes(q: String, sort: String, page: Int, size: Int, active: Boolean): Page<PostSubtype> =
        handleResponse { postSubtypeService.getSubtypes(q, sort, page, size, active) }

    suspend fun getSubtypeById(id: Int): PostSubtype =
        handleResponse { postSubtypeService.getSubtypeById(id) }

    suspend fun getSubtypesByType(id: Int, page: Int, size: Int): Page<PostSubtype> =
        handleResponse { postSubtypeService.getSubtypesByType(id, page, size) }

    suspend fun createSubtype(request: PostSubtypeRequest): PostSubtype =
        handleResponse { postSubtypeService.createSubtype(request) }

    suspend fun updateSubtype(id: Int, request: PostSubtypeRequest): PostSubtype =
        handleResponse { postSubtypeService.updateSubtype(id, request) }

    suspend fun deleteSubtype(id: Int) {
        handleResponse<Void> { postSubtypeService.deleteSubtype(id) }
    }
}