package frgp.utn.edu.ar.quepasa.domain.repository.media

import frgp.utn.edu.ar.quepasa.data.model.media.EventPicture
import frgp.utn.edu.ar.quepasa.data.source.remote.media.EventPictureService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.util.UUID
import javax.inject.Inject

class EventPictureRepository @Inject constructor(
    private val pictureService: EventPictureService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T & Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.errorBody()}")
        }
    }

    suspend fun getPictureById(id: UUID): EventPicture =
        handleResponse { pictureService.getPictureById(id) }

    suspend fun getPicturesByEvent(id: UUID, page: Int, size: Int): Page<EventPicture> =
        handleResponse { pictureService.getPicturesByEvent(id, page, size) }

    suspend fun upload(file: File, event: UUID, description: String): EventPicture? {
        val fileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

        val eventRequestBody = event.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaTypeOrNull())

        return pictureService.upload(filePart, eventRequestBody, descriptionRequestBody).body()
    }

    suspend fun deletePicture(id: UUID): Void =
        handleResponse { pictureService.deletePicture(id) }
}