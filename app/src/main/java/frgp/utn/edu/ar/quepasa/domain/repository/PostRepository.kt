package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.request.PostCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PostPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.source.remote.PostService
import org.w3c.dom.Comment
import retrofit2.http.Query
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {
    suspend fun getPosts(page: Int, size: Int, activeOnly: Boolean): List<Post> {
        val response = postService.getPosts(page, size, activeOnly)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPosts(q: String, sort: String, page: Int, size: Int, active: Boolean): List<Post> {
        val response = postService.getPosts(q, sort, page, size, active)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostById(id: Int): Post? {
        val response = postService.getPostById(id)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByOp(id: Int, page: Int, size: Int): List<Post> {
        val response = postService.getPostsByOp(id, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByAudience(audience: Audience, page: Int, size: Int): List<Post> {
        val response = postService.getPostsByAudience(audience, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByType(id: Int, page: Int, size: Int): List<Post> {
        val response = postService.getPostsByType(id, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsBySubtype(id: Int, page: Int, size: Int): List<Post> {
        val response = postService.getPostsBySubtype(id, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByDateRange(start: String, end: String, page: Int, size: Int): List<Post> {
        val response = postService.getPostsByDateRange(start, end, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByDateStart(start: String, page: Int, size: Int): List<Post> {
        val response = postService.getPostsByDateStart(start, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByDateEnd(end: String, page: Int, size: Int): List<Post> {
        val response = postService.getPostsByDateEnd(end, page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getPostsByAuthUser(page: Int, size: Int): List<Post> {
        val response = postService.getPostsByAuthUser(page, size)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun createPost(request: PostCreateRequest): Post? {
        val response = postService.createPost(request)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun updatePost(id: Int, request: PostPatchEditRequest): Post? {
        val response = postService.updatePost(id, request)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun deletePost(id: Int) {
        val response = postService.deletePost(id)
        if(!response.isSuccessful) {
            throw Exception(response.message())
        }
    }

    suspend fun getVotes(id: Int): VoteCount? {
        val response = postService.getVotes(id)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun upVote(id: Int): VoteCount? {
        val response = postService.upVote(id)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun downVote(id: Int): VoteCount? {
        val response = postService.downVote(id)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getComments(id: Int): List<Comment> {
        val response = postService.getComments(id)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        }
        else {
            throw Exception(response.message())
        }
    }

    suspend fun getComments(id: Int, content: String): Comment? {
        val response = postService.comment(id, content)
        return if (response.isSuccessful) {
            response.body()
        }
        else {
            throw Exception(response.message())
        }
    }
}