package frgp.utn.edu.ar.quepasa.domain.repository.commenting

import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.commenting.AbsComment
import frgp.utn.edu.ar.quepasa.data.source.remote.commenting.CommentService
import frgp.utn.edu.ar.quepasa.domain.repository.ApiResponseHandler
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentService: CommentService
) {

    private val handler: ApiResponseHandler = ApiResponseHandler()

    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T & Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.errorBody()}")
        }
    }

    suspend fun upVoteComment(id: UUID): VoteCount =
        handleResponse { commentService.upVoteComment(id) }

    suspend fun upvote(id: UUID): ApiResponse<VoteCount?> = handler.getResponse(commentService.upVoteComment(id))
    suspend fun downvote(id: UUID): ApiResponse<VoteCount?> = handler.getResponse(commentService.downVoteComment(id))

    suspend fun downVoteComment(id: UUID): VoteCount =
        handleResponse { commentService.downVoteComment(id) }

    suspend fun getCommentById(id: UUID): AbsComment =
        handleResponse { commentService.getCommentById(id) }

    suspend fun getCommentByIdLegacy(id: UUID): Comment =
        handleResponse { commentService.getCommentByIdLegacy(id) }

    suspend fun findById(id:UUID): ApiResponse<AbsComment?> = handler.getResponse(commentService.getCommentById(id))

    suspend fun updateComment(id: UUID, content: String): Comment =
        handleResponse { commentService.updateComment(id, content) }

    suspend fun deleteComment(id: UUID): ApiResponse<Void?> {
        return handler.getResponse(commentService.deleteComment(id))
    }

    suspend fun update(id: UUID, content: String): ApiResponse<Comment?> =
        handler.getResponse(commentService.updateComment(id, content));
}