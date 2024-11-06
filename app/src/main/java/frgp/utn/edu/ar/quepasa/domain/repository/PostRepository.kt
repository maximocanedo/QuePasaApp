package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.request.PostCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PostPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostDTO
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.source.remote.PostService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import org.w3c.dom.Comment
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T & Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }

    suspend fun getPosts(page: Int, size: Int, activeOnly: Boolean): Page<Post> =
        handleResponse { postService.getPosts(page, size, activeOnly) }

    suspend fun getPosts(q: String, sort: String, page: Int, size: Int, active: Boolean): Page<Post> =
        handleResponse { postService.getPosts(q, sort, page, size, active) }

    suspend fun getPostById(id: Int): Post =
        handleResponse { postService.getPostById(id) }

    suspend fun getPostsByOp(id: Int, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByOp(id, page, size) }

    suspend fun getPostsByAudience(audience: Audience, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByAudience(audience, page, size) }

    suspend fun getPostsByType(id: Int, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByType(id, page, size) }

    suspend fun getPostsBySubtype(id: Int, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsBySubtype(id, page, size) }

    suspend fun getPostsByDateRange(start: String, end: String, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByDateRange(start, end, page, size) }

    suspend fun getPostsByDateStart(start: String, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByDateStart(start, page, size) }

    suspend fun getPostsByDateEnd(end: String, page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByDateEnd(end, page, size) }

    suspend fun getPostsByAuthUser(page: Int, size: Int): Page<Post> =
        handleResponse { postService.getPostsByAuthUser(page, size) }

    suspend fun createPost(request: PostCreateRequest): Post =
        handleResponse { postService.createPost(request) }

    suspend fun updatePost(id: Int, request: PostPatchEditRequest): Post =
        handleResponse { postService.updatePost(id, request) }

    suspend fun deletePost(id: Int) {
        handleResponse<Void> { postService.deletePost(id) }
    }

    suspend fun getVotes(id: Int): VoteCount =
        handleResponse { postService.getVotes(id) }

    suspend fun upVote(id: Int): VoteCount =
        handleResponse { postService.upVote(id) }

    suspend fun downVote(id: Int): VoteCount =
        handleResponse { postService.downVote(id) }

    suspend fun getComments(id: Int): Page<Comment> =
        handleResponse { postService.getComments(id) }

    suspend fun comment(id: Int, content: String): Comment =
        handleResponse { postService.comment(id, content) }

    suspend fun findPosts(userId: Int, userNeighbourhood: Int, page: Int, size: Int): Page<PostDTO> {
        return handleResponse { postService.findPosts(userId, userNeighbourhood, page, size) }
    }
}
