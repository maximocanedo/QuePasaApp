package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.request.PostCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PostPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.source.remote.PostService
import org.w3c.dom.Comment
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }

    suspend fun getPosts(page: Int, size: Int, activeOnly: Boolean): List<Post> =
        handleResponse { postService.getPosts(page, size, activeOnly) } ?: emptyList()

    suspend fun getPosts(q: String, sort: String, page: Int, size: Int, active: Boolean): List<Post> =
        handleResponse { postService.getPosts(q, sort, page, size, active) } ?: emptyList()

    suspend fun getPostById(id: Int): Post? =
        handleResponse { postService.getPostById(id) }

    suspend fun getPostsByOp(id: Int, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByOp(id, page, size) } ?: emptyList()

    suspend fun getPostsByAudience(audience: Audience, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByAudience(audience, page, size) } ?: emptyList()

    suspend fun getPostsByType(id: Int, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByType(id, page, size) } ?: emptyList()

    suspend fun getPostsBySubtype(id: Int, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsBySubtype(id, page, size) } ?: emptyList()

    suspend fun getPostsByDateRange(start: String, end: String, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByDateRange(start, end, page, size) } ?: emptyList()

    suspend fun getPostsByDateStart(start: String, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByDateStart(start, page, size) } ?: emptyList()

    suspend fun getPostsByDateEnd(end: String, page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByDateEnd(end, page, size) } ?: emptyList()

    suspend fun getPostsByAuthUser(page: Int, size: Int): List<Post> =
        handleResponse { postService.getPostsByAuthUser(page, size) } ?: emptyList()

    suspend fun createPost(request: PostCreateRequest): Post? =
        handleResponse { postService.createPost(request) }

    suspend fun updatePost(id: Int, request: PostPatchEditRequest): Post? =
        handleResponse { postService.updatePost(id, request) }

    suspend fun deletePost(id: Int) {
        handleResponse<Void> { postService.deletePost(id) }
    }

    suspend fun getVotes(id: Int): VoteCount? =
        handleResponse { postService.getVotes(id) }

    suspend fun upVote(id: Int): VoteCount? =
        handleResponse { postService.upVote(id) }

    suspend fun downVote(id: Int): VoteCount? =
        handleResponse { postService.downVote(id) }

    suspend fun getComments(id: Int): List<Comment> =
        handleResponse { postService.getComments(id) } ?: emptyList()

    suspend fun comment(id: Int, content: String): Comment? =
        handleResponse { postService.comment(id, content) }
}
