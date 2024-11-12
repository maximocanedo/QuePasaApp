package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostTypeService {
    @POST("post-types")
    suspend fun createType(@Body description: String): Response<PostType>

    @GET("post-types/all")
    suspend fun getTypes(@Query("page") page: Int, @Query("size") size: Int, @Query("activeOnly") activeOnly: Boolean): Response<Page<PostType>>

    @GET("post-types/search")
    suspend fun getTypes(@Query("q") q: String, @Query("sort") sort: String, @Query("page") page: Int, @Query("size") size: Int, @Query("active") active: Boolean): Response<Page<PostType>>

    @GET("post-types/{id}")
    suspend fun getTypeById(@Path("id") id: Int): Response<PostType>

    @GET("post-types/subtype/{id}")
    suspend fun getTypesBySubtype(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<PostType>>

    @PATCH("post-types/{id}")
    suspend fun updateType(@Path("id") id: Int, @Body description: String): Response<PostType>

    @DELETE("post-types/{id}")
    suspend fun deleteType(@Path("id") id: Int): Response<Void>
}