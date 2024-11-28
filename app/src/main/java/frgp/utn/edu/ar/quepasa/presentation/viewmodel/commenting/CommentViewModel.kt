package frgp.utn.edu.ar.quepasa.presentation.viewmodel.commenting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.domain.repository.commenting.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository: CommentRepository
): ViewModel() {
    private val _comment = MutableStateFlow<Comment?>(null)
    val comment: StateFlow<Comment?> get() = _comment

    private val _errorMessage = MutableStateFlow<String?>(null)


    suspend fun upVoteComment(id: UUID) {
        try {
            repository.upVoteComment(id)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun downVoteComment(id: UUID) {
        try {
            repository.downVoteComment(id)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getCommentById(id: UUID) {
        try {
            val comment = repository.getCommentByIdLegacy(id)
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

    suspend fun deleteComment(id: UUID) {
        try {
            val comment = repository.deleteComment(id)

        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }
}