package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.dto.request.PostSubtypeRequest
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostSubtypeService {
    @POST("post-subtypes")
    suspend fun createSubtype(@Body request: PostSubtypeRequest): Response<PostSubtype>

    @GET("post-subtypes/all")
    suspend fun getSubtypes(@Query("page") page: Int, @Query("size") size: Int, @Query("activeOnly") activeOnly: Boolean): Response<Page<PostSubtype>>

    @GET("post-subtypes/search")
    suspend fun getSubtypes(@Query("q") q: String, @Query("sort") sort: String, @Query("page") page: Int, @Query("size") size: Int, @Query("active") active: Boolean): Response<Page<PostSubtype>>

    @GET("post-subtypes/{id}")
    suspend fun getSubtypeById(@Path("id") id: Int): Response<PostSubtype>

    @GET("post-subtypes/type/{id}")
    suspend fun getSubtypesByType(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<PostSubtype>>

    @PATCH("post-subtypes/{id}")
    suspend fun updateSubtype(@Path("id") id: Int, @Body request: PostSubtypeRequest): Response<PostSubtype>

    @DELETE("post-subtypes/{id}")
    suspend fun deleteSubtype(@Path("id") id: Int): Response<Void>
}