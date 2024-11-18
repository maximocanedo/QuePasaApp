package frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.MutableSharedFlow
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

    private val _fieldsValidation: List<MutableStateFlow<Boolean>> = List(2) { MutableStateFlow(false) }

    private val _fieldTagValidation = MutableSharedFlow<Boolean>()

    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _isLoading = MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _allPosts = MutableLiveData<List<Post>>()
    val allPosts: LiveData<List<Post>> get() = _allPosts

    private val _filteredPosts = MutableLiveData<List<Post>>()
    val filteredPosts: LiveData<List<Post>> get() = _filteredPosts

    init {
        viewModelScope.launch {
            getPosts(0, 5,  true)
        }
    }

    fun filterPostsByTag(tag: String) {
        val filtered = _posts.value.content.filter { post ->
            post.tags?.split(",")?.contains(tag) == true
        }
        _filteredPosts.value = filtered
    }

    fun clearFilter() {
        _filteredPosts.value = _posts.value.content
    }

    suspend fun getPosts(page: Int = 0, size: Int = 5, activeOnly: Boolean = true) {
        try {
            val newPosts = repository.getPosts(page, size, activeOnly)
            _posts.value = if (page == 0) {
                newPosts
            } else {
                _posts.value.copy(
                    content = _posts.value.content + newPosts.content,
                    totalElements = newPosts.totalElements,
                    totalPages = newPosts.totalPages,
                    pageNumber = newPosts.pageNumber
                )
            }
        } catch (e: Exception) {
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
            _tags.value = _post.value!!.tags?.split(",") ?: emptyList()
            _tagCount.value = _tags.value.size
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
    val isRefreshing = MutableStateFlow(false)

    suspend fun refreshPosts() {
        isRefreshing.value = true
        try {
            getPosts(page = 0, size = 5, activeOnly = true)
        } finally {
            isRefreshing.value = false
        }
    }

    suspend fun createPost(
        audience: String,
        title: String,
        subtype: Int,
        description: String,
        neighbourhood: Long,
        tags: List<String>
    ): Boolean {
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
                subtype = subtype,
                description = description,
                neighbourhood = neighbourhood,
                timestamp = null,
                tags = tagsString
            )
            println("New post: Title ${request.title} + Audience ${request.audience} + Subtype ${request.subtype} + Desc ${request.description} + Neigh ${request.neighbourhood} + Tags ${request.tags}")
            val newPost = repository.createPost(request)
            _post.value = newPost
            clearTags()
            return true
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
            e.printStackTrace()
            return false
        }
    }

    suspend fun updatePost(
        id: Int,
        audience: String,
        title: String,
        subtype: Int,
        description: String,
        neighbourhood: Long,
        tags: List<String>
    ): Boolean {
        try {
            var tagsString = ""
            tags.forEachIndexed { index, tag ->
                tagsString += tag
                if(index < tags.size - 1) tagsString += ","
            }
            val request = PostPatchEditRequest(
                audience = Audience.valueOf(audience),
                title = title,
                subtype = subtype,
                description = description,
                neighbourhood = neighbourhood,
                tags = tagsString
            )
            println("Updated post: Title ${request.title} + Audience ${request.audience} + Subtype ${request.subtype} + Desc ${request.description} + Neigh ${request.neighbourhood} + Tags ${request.tags}")
            val updatedPost = repository.updatePost(id, request)
            _post.value = updatedPost
            clearTags()
            return true
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
            return false
        }
    }

    suspend fun deletePost(id: Int) {
        try {
            repository.deletePost(id)
            getPosts(0, 5, true)
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
            _isLoading.value = true
            val votes = repository.upVote(id)
            _votes.value = votes
            _posts.value = _posts.value.copy(
                content = _posts.value.content.map { post ->
                    if (post.id == id) {
                        post.copy(votes = votes)
                    } else {
                        post
                    }
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }finally {
            _isLoading.value = false

        }
    }

    suspend fun downVote(id: Int) {
        try {
            _isLoading.value = true
            val votes = repository.downVote(id)
            _votes.value = votes
            _posts.value = _posts.value.copy(
                content = _posts.value.content.map { post ->
                    if (post.id == id) {
                        post.copy(votes = votes)
                    } else {
                        post
                    }
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }finally {
            _isLoading.value = false
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

    fun toggleValidationField(index: Int, state: Boolean) {
        require(index in _fieldsValidation.indices) { "Index out of bounds" }
        _fieldsValidation[index].value = state
    }

    fun checkValidationFields(): Boolean {
        return _fieldsValidation.all { it.value }
    }

    fun toggleTagValidationField(state: Boolean) {
        viewModelScope.launch {
            _fieldTagValidation.emit(state)
        }
    }
}