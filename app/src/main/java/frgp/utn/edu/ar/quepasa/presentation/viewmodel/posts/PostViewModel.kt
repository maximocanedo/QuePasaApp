package frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.request.PostCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PostPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostDTO
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.domain.repository.PostRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
): ViewModel() {
    private val _posts = MutableStateFlow<Page<Post>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val posts: StateFlow<Page<Post>> get() = _posts

    private val _postsDTO = MutableStateFlow<Page<PostDTO>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val postsDTO: StateFlow<Page<PostDTO>> get() = _postsDTO

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> get() = _post

    private val _votes = MutableStateFlow<VoteCount?>(null)
    val votes: StateFlow<VoteCount?> get() = _votes

    private val _comments = MutableStateFlow<Page<Comment>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val comments: StateFlow<Page<Comment>> get() = _comments

    private val _comment = MutableStateFlow<Comment?>(null)
    val comment: StateFlow<Comment?> get() = _comment

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> get() = _tags

    private var _tagCount = MutableStateFlow(0)
    val tagCount: StateFlow<Int> get() = _tagCount

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        viewModelScope.launch {
            getPosts(0, 10,  true)
        }
    }

    private suspend fun getPosts(page: Int, size: Int, activeOnly: Boolean) {
        try {
            val posts = repository.getPosts(page, size, activeOnly)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPosts(q: String, sort: String, page: Int, size: Int, active: Boolean) {
        try {
            val posts = repository.getPosts(q, sort, page, size, active)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostById(id: Int) {
        try {
            val post = repository.getPostById(id)
            _post.value = post
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByOp(id: Int, page: Int, size: Int) {
        try {
            val posts = repository.getPostsByOp(id, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByAudience(audience: Audience, page: Int, size: Int) {
        try {
            val posts = repository.getPostsByAudience(audience, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByType(id: Int, page: Int, size: Int) {
        try {
            val posts = repository.getPostsByType(id, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsBySubtype(id: Int, page: Int, size: Int) {
        try {
            val posts = repository.getPostsBySubtype(id, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByDateRange(start: String, end: String, page: Int, size: Int) {
        try {
            val posts = repository.getPostsByDateRange(start, end, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByDateStart(start: String, page: Int, size: Int) {
        try {
            val posts = repository.getPostsByDateStart(start, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByDateEnd(end: String, page: Int, size: Int) {
        try {
            val posts = repository.getPostsByDateEnd(end, page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPostsByAuthUser(page: Int, size: Int) {
        try {
            val posts = repository.getPostsByAuthUser(page, size)
            _posts.value = posts
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun createPost(
        audience: String,
        title: String,
        subtype: String,
        description: String,
        neighbourhood: Long,
        tags: List<String>
    ) {
        try {
            var tagsString = ""
            tags.forEachIndexed { index, tag ->
                tagsString += tag
                if(index < tags.size - 1) tagsString += ","
            }
            val request = PostCreateRequest(
                originalPoster = null,
                audience = Audience.valueOf(audience),
                title = title,
                subtype = subtype.toInt(),
                description = description,
                neighbourhood = neighbourhood,
                timestamp = null,
                tags = tagsString
            )
            println("New post: Title ${request.title} + Audience ${request.audience} + Subtype ${request.subtype} + Desc ${request.description} + Neigh ${request.neighbourhood} + Tags ${request.tags}")
            val newType = repository.createPost(request)
            _post.value = newType
            clearTags()
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
            println(e.printStackTrace())
        }
    }

    suspend fun updatePost(id: Int, request: PostPatchEditRequest) {
        try {
            val newType = repository.updatePost(id, request)
            _post.value = newType
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deletePost(id: Int) {
        try {
            repository.deletePost(id)
            getPosts(0, 10, true)
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getVotes(id: Int) {
        try {
            val votes = repository.getVotes(id)
            _votes.value = votes
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun upVote(id: Int) {
        try {
            val votes = repository.upVote(id)
            _votes.value = votes
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun downVote(id: Int) {
        try {
            val votes = repository.downVote(id)
            _votes.value = votes
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getComments(id: Int) {
        try {
            val comments = repository.getComments(id)
            _comments.value = comments
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun comment(id: Int, content: String) {
        try {
            val comment = repository.comment(id, content)
            _comment.value = comment
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun findPosts(userId: Int, userNeighbourhood: Int, page: Int, size: Int) {
        try {
            val postsDTO = repository.findPosts(userId, userNeighbourhood, page, size)
            _postsDTO.value = postsDTO
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun getTags(): List<String> {
        return tags.value
    }

    fun addTag(tag: String) {
        if(tagCount.value < 5) {
            val tags = _tags.value + tag
            _tags.value = tags
            _tagCount.value = _tags.value.size
        }
    }

    fun removeTag(tag: String) {
        var tags: List<String> = emptyList()
        _tags.value.forEach { tagValue ->
            if(tagValue != tag) tags = tags + tagValue
        }
        _tags.value = tags
        _tagCount.value = _tags.value.size
    }

    private fun clearTags() {
        _tags.value = emptyList()
        _tagCount.value = 0
    }
}