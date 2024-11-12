package frgp.utn.edu.ar.quepasa.domain.repository.media

import frgp.utn.edu.ar.quepasa.data.model.media.PostPicture
import frgp.utn.edu.ar.quepasa.data.source.remote.media.PostPictureService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import javax.inject.Inject

class PostPictureRepository @Inject constructor(
    private val pictureService: PostPictureService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T & Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.errorBody()}")
        }
    }

    suspend fun getPictureById(id: Int): PostPicture =
        handleResponse { pictureService.getPictureById(id) }

    suspend fun getPicturesByPost(id: Int, page: Int, size: Int): Page<PostPicture> =
        handleResponse { pictureService.getPicturesByPost(id, page, size) }

    suspend fun upload(): PostPicture? {
        // TODO: Not implemented yet
        return null
    }

    suspend fun deletePicture(id: Int): Void =
        handleResponse { pictureService.deletePicture(id) }
}