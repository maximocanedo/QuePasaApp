@file:Suppress("UNCHECKED_CAST")

package frgp.utn.edu.ar.quepasa.presentation.ui.components.comment

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.error
import frgp.utn.edu.ar.quepasa.data.dto.handle
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.commenting.AbsComment
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.domain.repository.PostRepository
import frgp.utn.edu.ar.quepasa.domain.repository.commenting.CommentRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CommentCardViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
): ViewModel() {

    val comment = MutableStateFlow<AbsComment?>(null)
    val loading = MutableStateFlow<Boolean>(false)
    val votes = MutableStateFlow<VoteCount?>(null)
    val fail = MutableSharedFlow<Fail?>(1)

    suspend fun load(p: AbsComment? = null, id: UUID?) {
        loading.update { true }
        if(p != null) {
            comment.update { p }
            votes.update { p.votes }
            loading.update { false }
            return
        }
        else if(id == null) {
            loading.update { false }
            return
        }

        commentRepository
            .findById(id)
            .handle { x ->
                if(x != null) comment.update { x }
                if(x != null) votes.update { x.votes }
                loading.update { false }
            }
            .error {
                loading.update { false }
                fail.tryEmit(it)
            }

    }




    suspend fun upvote() {
        if(comment.value == null) return
        if(votes.value != null) {
            votes.update {
                if(it == null) return@update null
                VoteCount(
                    uservote = if (it.uservote == 1) 0 else 1,
                    votes = if(it.uservote == 1) it.votes - 1
                    else if(it.uservote == 0) it.votes + 1
                    else it.votes + 2,
                    updated = it.updated
                )
            }
        }

        commentRepository
            .upvote(comment.value!!.id)
            .handle { v ->
                votes.update { v }
            }
            .error {
                fail.tryEmit(it)
            }
    }

    suspend fun downvote() {
        if(comment.value == null) return
        if(votes.value != null) {
            votes.update {
                if(it == null) return@update null
                VoteCount(
                    uservote = if (it.uservote == -1) 0 else -1,
                    votes = if(it.uservote == 1) it.votes - 2
                    else if(it.uservote == 0) it.votes - 1
                    else it.votes + 1,
                    updated = it.updated
                )
            }
        }

        commentRepository
            .downvote(comment.value!!.id)
            .handle { v ->
                votes.update { v }
            }
            .error {
                fail.tryEmit(it)
            }
    }



}