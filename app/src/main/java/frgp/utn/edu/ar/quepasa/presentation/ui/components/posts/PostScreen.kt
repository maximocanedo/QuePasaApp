package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import PostCard
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TrendsScreen
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import frgp.utn.edu.ar.quepasa.R

@Composable
fun PostScreen(

) {
    val postViewModel: PostViewModel = hiltViewModel()
    val postsState = postViewModel.posts.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        
        TrendsScreen()


        postsState.value.content.forEach { post ->
            PostCard(
                post = post,
                onLikeClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        postViewModel.upVote(post.id!!)
                    }
                },
                onDislikeClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        postViewModel.downVote(post.id!!)
                    }
                },
                onCommentClick = {
                },
                onEditClick = { postId ->
                }
            )
        }
    }


}
