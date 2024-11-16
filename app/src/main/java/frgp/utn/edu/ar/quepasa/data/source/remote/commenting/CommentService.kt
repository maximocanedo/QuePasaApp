package frgp.utn.edu.ar.quepasa.data.source.remote.commenting

import frgp.utn.edu.ar.quepasa.data.dto.request.PostCommentDTO
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.commenting.EventComment
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface CommentService {
    @POST("comments/post")
    suspend fun createPostComment(@Body file: PostCommentDTO): Response<Comment>

    @POST("/api/events/{id}/comments")
    suspend fun createEventComment(@Path("id") eventId: UUID, @Body file: String): Response<Comment>

    @GET("/comments/posts/{id}")
    suspend fun getCommentsByPost(@Path("id") id: Int, @Query("page") page: Int, @Query("size") size: Int): Response<Page<PostComment>>

    @GET("/api/events/{id}/comments")
    suspend fun getCommentsByEvent(@Path("id") id: UUID, @Query("page") page: Int, @Query("size") size: Int): Response<Page<EventComment>>

    @GET("/comments/{id}")
    suspend fun getCommentById(@Path("id") id: UUID): Response<Comment>

    @PATCH("/comments/{id}")
    suspend fun updateComment(@Path("id") id: UUID, @Body content: String): Response<Comment>

    @DELETE("/comments/{id}")
    suspend fun deleteComment(@Path("id") id: UUID): Response<Void>
}