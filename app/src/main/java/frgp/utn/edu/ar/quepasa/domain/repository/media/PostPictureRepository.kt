package frgp.utn.edu.ar.quepasa.domain.repository.media

import frgp.utn.edu.ar.quepasa.data.model.media.PostPicture
import frgp.utn.edu.ar.quepasa.data.source.remote.media.PostPictureService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.util.UUID
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

    suspend fun upload(file: File, post: Int, description: String): PostPicture? {
        val fileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

        val postRequestBody = post.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaTypeOrNull())

        return pictureService.upload(filePart, postRequestBody, descriptionRequestBody).body()
    }

    suspend fun deletePicture(id: UUID): Void =
        handleResponse { pictureService.deletePicture(id) }
}