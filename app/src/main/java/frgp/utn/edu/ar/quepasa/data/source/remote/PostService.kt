package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.dto.request.PostCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PostPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostDTO
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @POST("posts")
    suspend fun createPost(@Body post: PostCreateRequest): Response<Post>

    @GET("posts/all")
    suspend fun getPosts(@Query("page") page: Int, @Query("size") size: Int, @Query("activeOnly") activeOnly: Boolean): Response<Page<Post>>

    @GET("posts/search")
    suspend fun getPosts(@Query("q") q: String, @Query("sort") sort: String, @Query("page") page: Int, @Query("size") size: Int, @Query("active") active: Boolean): Response<Page<Post>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

    @GET("posts/op/{id}")
    suspend fun getPostsByOp(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/audience/{audience}")
    suspend fun getPostsByAudience(@Path("audience") audience: Audience, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/type/{id}")
    suspend fun getPostsByType(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/subtype/{id}")
    suspend fun getPostsBySubtype(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/date/{start}/{end}")
    suspend fun getPostsByDateRange(@Path("start") start: String, @Path("end") end: String, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/date-start/{start}")
    suspend fun getPostsByDateStart(@Path("start") start: String, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/date-end/{end}")
    suspend fun getPostsByDateEnd(@Path("end") end: String, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @GET("posts/me")
    suspend fun getPostsByAuthUser(@Query("page") page: Int, @Query("size") size: Int): Response<Page<Post>>

    @PATCH("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostPatchEditRequest): Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Void>

    /** Comienza sección de VOTOS **/
    @GET("posts/{id}/votes")
    suspend fun getVotes(@Path("id") id: Int): Response<VoteCount>

    @POST("posts/{id}/votes/up")
    suspend fun upVote(@Path("id") id: Int): Response<VoteCount>

    @POST("posts/{id}/votes/down")
    suspend fun downVote(@Path("id") id: Int): Response<VoteCount>
    /** Termina sección de VOTOS **/

    /** Comienza sección de COMENTARIOS **/
    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): Response<Page<PostComment>>

    @POST("posts/{id}/comments")
    suspend fun comment(@Path("id") id: Int, @Body content: String): Response<PostComment>
    /** Termina sección de COMENTARIOS **/

    /** Algoritmo de post **/
    @GET("posts/user/{userId}/neighbourhood/{userNeighbourhood}")
    suspend fun findPosts(
        @Path("userId") userId: Int,
        @Path("userNeighbourhood") userNeighbourhood: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<Page<PostDTO>>


}