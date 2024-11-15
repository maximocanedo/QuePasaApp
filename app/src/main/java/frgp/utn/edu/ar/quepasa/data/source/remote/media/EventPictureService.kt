package frgp.utn.edu.ar.quepasa.data.source.remote.media

import frgp.utn.edu.ar.quepasa.data.model.media.EventPicture
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface EventPictureService {
    @Multipart
    @POST("event-pictures")
    suspend fun upload(
        @Part file: MultipartBody.Part,
        @Part("eventId") event: RequestBody,
        @Part("description") description: RequestBody
    ): Response<EventPicture>

    @GET("event-pictures/{id}")
    suspend fun getPictureById(@Path("id") id: UUID): Response<EventPicture>

    @GET("event-pictures/event/{id}")
    suspend fun getPicturesByEvent(
        @Path("id") id: UUID,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<Page<EventPicture>>

    @DELETE("event-pictures/{id}")
    suspend fun deletePicture(@Path("id") id: UUID): Response<Void>
}