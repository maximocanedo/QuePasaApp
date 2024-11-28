package frgp.utn.edu.ar.quepasa.data.source.remote.commenting

import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.commenting.AbsComment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface CommentService {
    @POST("comments/{commentId}/votes/up")
    suspend fun upVoteComment(@Path("commentId") commentId: UUID): Response<VoteCount>

    @POST("comments/{commentId}/votes/down")
    suspend fun downVoteComment(@Path("commentId") commentId: UUID): Response<VoteCount>

    @GET("comments/{commentId}")
    suspend fun getCommentById(@Path("commentId") commentId: UUID): Response<AbsComment>
    @GET("comments/{commentId}")
    suspend fun getCommentByIdLegacy(@Path("commentId") commentId: UUID): Response<Comment>

    @PATCH("comments/{commentId}")
    suspend fun updateComment(
        @Path("commentId") commentId: UUID,
        @Body content: String
    ): Response<Comment>

    @DELETE("comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: UUID): Response<Void>
}