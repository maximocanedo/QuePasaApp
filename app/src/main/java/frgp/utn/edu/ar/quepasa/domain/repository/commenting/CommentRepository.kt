package frgp.utn.edu.ar.quepasa.domain.repository.commenting

import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.source.remote.commenting.CommentService
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentService: CommentService
) {
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

    suspend fun downVoteComment(id: UUID): VoteCount =
        handleResponse { commentService.downVoteComment(id) }

    suspend fun getCommentById(id: UUID): Comment =
        handleResponse { commentService.getCommentById(id) }

    suspend fun updateComment(id: UUID, content: String): Comment =
        handleResponse { commentService.updateComment(id, content) }

    suspend fun deleteComment(id: UUID) {
        commentService.deleteComment(id)
    }
}