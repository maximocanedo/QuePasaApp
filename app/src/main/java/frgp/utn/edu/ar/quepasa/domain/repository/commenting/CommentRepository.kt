package frgp.utn.edu.ar.quepasa.domain.repository.commenting

import frgp.utn.edu.ar.quepasa.data.dto.request.EventCommentDTO
import frgp.utn.edu.ar.quepasa.data.dto.request.PostCommentDTO
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.commenting.EventComment
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.data.source.remote.commenting.CommentService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
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

    suspend fun getCommentsByPost(id: Int, page: Int, size: Int): Page<PostComment> =
        handleResponse { commentService.getCommentsByPost(id, page, size) }

    suspend fun getCommentsByEvent(id: UUID, page: Int, size: Int): Page<EventComment> =
        handleResponse { commentService.getCommentsByEvent(id, page, size) }

    suspend fun getCommentById(id: UUID): Comment =
        handleResponse { commentService.getCommentById(id) }

    suspend fun createPostComment(file: PostCommentDTO): Comment =
        handleResponse { commentService.createPostComment(file) }

    suspend fun createEventComment(file: EventCommentDTO): Comment =
        handleResponse { commentService.createEventComment(file) }

    suspend fun updateComment(id: UUID, content: String): Comment =
        handleResponse { commentService.updateComment(id, content) }

    suspend fun deleteComment(id: UUID) {
        commentService.deleteComment(id)
    }
}