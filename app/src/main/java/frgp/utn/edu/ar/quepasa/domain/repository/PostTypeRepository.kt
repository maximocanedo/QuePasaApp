package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.data.source.remote.PostTypeService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import javax.inject.Inject

class PostTypeRepository @Inject constructor(
    private val postTypeService: PostTypeService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }

    suspend fun getTypes(page: Int, size: Int, activeOnly: Boolean): Page<PostType> =
        handleResponse { postTypeService.getTypes(page, size, activeOnly) }

    suspend fun getTypes(q: String, sort: String, page: Int, size: Int, active: Boolean): Page<PostType> =
        handleResponse { postTypeService.getTypes(q, sort, page, size, active) }

    suspend fun getTypeById(id: Int): PostType =
        handleResponse { postTypeService.getTypeById(id) }

    suspend fun getTypesBySubtype(id: Int, page: Int, size: Int): Page<PostType> =
        handleResponse { postTypeService.getTypesBySubtype(id, page, size) }

    suspend fun createType(description: String): PostType =
        handleResponse { postTypeService.createType(description) }

    suspend fun updateType(id: Int, description: String): PostType =
        handleResponse { postTypeService.updateType(id, description) }

    suspend fun deleteType(id: Int) {
        handleResponse<Void> { postTypeService.deleteType(id) }
    }
}