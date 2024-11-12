package frgp.utn.edu.ar.quepasa.data.source.remote.media

import frgp.utn.edu.ar.quepasa.data.model.media.PostPicture
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

interface PostPictureService {
    @Multipart
    @POST("post-pictures")
    suspend fun upload(@Part file: MultipartBody.Part, @Part("post") post: RequestBody, @Part("description") description: RequestBody): Response<PostPicture>

    @GET("post-pictures/{id}")
    suspend fun getPictureById(@Path("id") id: Int): Response<PostPicture>

    @GET("post-pictures/post/{id}")
    suspend fun getPicturesByPost(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<PostPicture>>

    @DELETE("post-pictures/{id}")
    suspend fun deletePicture(@Path("id") id: Int): Response<Void>
}