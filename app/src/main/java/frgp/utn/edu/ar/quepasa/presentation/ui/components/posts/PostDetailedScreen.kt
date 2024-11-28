package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import FullPost
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.comment.CommentDialog
import frgp.utn.edu.ar.quepasa.presentation.ui.components.comment.PostCommentCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.images.ImagesListPreview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.card.IndividualPostViewModel
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.commenting.CommentViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import frgp.utn.edu.ar.quepasa.utils.date.formatNumber
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun PostDetailedScreen(
    navController: NavHostController,
    postId: Int,
    vm: IndividualPostViewModel = hiltViewModel(key = "post@${postId}")
) {
    val user by LocalAuth.current.collectAsState()

    val postPictureViewModel: PostPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()
    val commentViewModel: CommentViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()

    val post by vm.post.collectAsState()
    val votes by vm.votes.collectAsState()
    // val comments by viewModel.comments.collectAsState()
    var commentDialogState by remember { mutableStateOf(false) }
    var commentEditState by remember { mutableStateOf(false) }
    var commentEditUUID by remember { mutableStateOf(UUID.randomUUID()) }
    var commentEditText by remember { mutableStateOf("") }

    LaunchedEffect(postId) {
        vm.load(id = postId)
    }

    LaunchedEffect(post) {
        if(post != null) {
            postPictureViewModel.getPicturesByPost(postId, 0, 10)
            pictureViewModel.setPicturesBitmap(postPictureViewModel.picturesId.value)
            // viewModel.getComments(postId)
        }
    }

    val bitmaps = pictureViewModel.pictures.collectAsState()

    if (post != null) {
        BaseComponent(
            navController = navController,
            title = "Publicación",
            back = true
        ) {
            Column(
                modifier = Modifier.padding(2.dp)
            ) {
                    FullPost(
                        postId = postId,
                        navController = navController,
                        onEditClick = {  },
                        onRemoveClick = {  }
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp)
                    ) { Text("Comentarios", style = MaterialTheme.typography.bodyMedium) }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
            }
        }
        if (commentDialogState) {
            CommentDialog(
                onDismissRequest = { commentDialogState = false },
                onConfirm = { content ->
                    /* viewModel.viewModelScope.launch {
                        viewModel.comment(postId, content)
                        viewModel.getComments(postId)
                    } */
                    commentDialogState = false
                }
            )
        }
        if (commentEditState) {
            CommentDialog(
                content = commentEditText,
                onDismissRequest = { commentEditState = false },
                onConfirm = { content ->
                    /* viewModel.viewModelScope.launch {
                        commentViewModel.updateComment(commentEditUUID, content)
                        viewModel.getComments(postId)
                    } */
                    commentEditText = ""
                    commentEditState = false
                }
            )
        }
    }
}

@Preview
@Composable
fun PostDetailedScreenPreview() {
    val navController = rememberNavController()
    val postId by remember { mutableStateOf(1) }
    PostDetailedScreen(navController = navController, postId = postId)
}