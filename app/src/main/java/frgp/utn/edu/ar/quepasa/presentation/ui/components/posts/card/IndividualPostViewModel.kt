package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.card

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.error
import frgp.utn.edu.ar.quepasa.data.dto.handle
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndividualPostViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    val post = MutableStateFlow<Post?>(null)
    val err = MutableSharedFlow<Fail?>(1)
    val loading = MutableStateFlow<Boolean>(false)
    var cl = MutableStateFlow<Boolean>(false)
    val votes = MutableStateFlow<VoteCount?>(null)

    val comments: MutableList<PostComment> = mutableStateListOf<PostComment>()

    suspend fun loadComments() {
        cl.update { true }
        if(post.value != null ){
            val size = comments.size
            val page = (size / 5) - (size % 5)
            postRepository
                .findComments(post.value!!.id, page, 5)
                .handle {
                    if(it != null) comments.addAll(it.content)
                    cl.update { false }
                }
                .error { cl.update { false } }
        }
    }

    suspend fun load(p: Post? = null, id: Int?) {
        loading.update { true }
        if(p != null) {
            post.update { p }
            votes.update { p.votes }
            viewModelScope.launch {
                loadComments()
            }
            loading.update { false }
            return
        }
        else if(id == null) {
            loading.update { false }
            return
        }
        postRepository
            .findPostById(id)
            .handle { n ->
                post.update { n }
                if(n != null) {
                    votes.update { n.votes }
                    viewModelScope.launch {
                        loadComments()
                    }
                }
                loading.update { false }
            }
            .error {
                err.tryEmit(it)
                loading.update { false }
            }
    }

    suspend fun upvote() {
        if (post.value == null) return


        if (votes.value != null) {
            votes.update {
                if (it == null) return@update null
                VoteCount(
                    uservote = if (it.uservote == 1) 0 else 1,
                    votes = if (it.uservote == 1) it.votes - 1
                    else if (it.uservote == 0) it.votes + 1
                    else it.votes + 2,
                    updated = it.updated
                )
            }
        }

        postRepository
            .upvote(post.value!!.id)
            .handle { v ->
                votes.update { v }
                if (v != null) Log.d("Votes: ", "UserVote: ${v.uservote}, Votes: ${v.votes}")
            }
            .error {
                err.tryEmit(it)
            }
    }

    suspend fun downvote() {
        if (post.value == null) return

        if (votes.value != null) {
            votes.update {
                if (it == null) return@update null
                VoteCount(
                    uservote = if (it.uservote == -1) 0 else -1,
                    votes = if (it.uservote == 1) it.votes - 2
                    else if (it.uservote == 0) it.votes - 1
                    else it.votes + 1,
                    updated = it.updated
                )
            }
        }

        postRepository
            .downvote(post.value!!.id)
            .handle { v ->
                votes.update { v }
            }
            .error {
                err.tryEmit(it)
            }
    }


}