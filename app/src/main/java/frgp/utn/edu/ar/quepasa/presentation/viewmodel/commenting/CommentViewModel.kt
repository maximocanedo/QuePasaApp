package frgp.utn.edu.ar.quepasa.presentation.viewmodel.commenting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.request.EventCommentDTO
import frgp.utn.edu.ar.quepasa.data.dto.request.PostCommentDTO
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.commenting.EventComment
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.domain.repository.commenting.CommentRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository: CommentRepository
): ViewModel() {
    private val _postComments = MutableStateFlow<Page<PostComment>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val postComments: StateFlow<Page<PostComment>> get() = _postComments

    private val _eventComments = MutableStateFlow<Page<EventComment>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val eventComments: StateFlow<Page<EventComment>> get() = _eventComments

    private val _comment = MutableStateFlow<Comment?>(null)
    val comment: StateFlow<Comment?> get() = _comment

    private val _errorMessage = MutableStateFlow<String?>(null)

    suspend fun getCommentsByPost(id: Int, page: Int, size: Int) {
        try {
            val comments = repository.getCommentsByPost(id, page, size)
            _postComments.value = comments
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getCommentsByEvent(id: UUID, page: Int, size: Int) {
        try {
            val comments = repository.getCommentsByEvent(id, page, size)
            _eventComments.value = comments
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getCommentById(id: UUID) {
        try {
            val comment = repository.getCommentById(id)
            _comment.value = comment
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun createPostComment(content: String, post: Post) {
        try {
            val postCommentDTO = PostCommentDTO(content, post)
            val comment = repository.createPostComment(postCommentDTO)
            _comment.value = comment
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun createEventComment(content: String, event: Event) {
        try {
            val eventCommentDTO = EventCommentDTO(content, event)
            val comment = repository.createEventComment(eventCommentDTO)
            _comment.value = comment
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun updateComment(id: UUID, content: String) {
        try {
            val comment = repository.updateComment(id, content)
            _comment.value = comment
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deleteComment(id: UUID, loadPosts: Boolean, idPost: Int = 1, idEvent: UUID = UUID.randomUUID()) {
        try {
            val comment = repository.deleteComment(id)
            if(loadPosts) {
                getCommentsByPost(idPost, 0, 10)
            }
            else {
                getCommentsByEvent(idEvent, 0, 10)
            }
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }
}