package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import PostCard
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TrendsScreen
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import kotlinx.coroutines.launch

@Composable
fun PostScreen(
    navController: NavHostController,
    selectedTag: String?
) {
    val postViewModel: PostViewModel = hiltViewModel()
    val postsState = postViewModel.posts.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val isLoading by postViewModel.isLoading.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        if (!selectedTag.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tendencia seleccionada: $selectedTag",
                    modifier = Modifier.weight(1f),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Limpiar filtro",
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("posts") {
                                popUpTo("posts") { inclusive = true }
                            }
                        }
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(1.dp),
            ) {
                val filteredPosts = postsState.value.content.filter { post ->
                    selectedTag == null || (post.tags?.contains(selectedTag) ?: false)
                }

                TrendsScreen(navController)

                filteredPosts.forEach { post ->
                    PostCard(
                        post = post,
                        navController = navController,
                        onLikeClick = {
                            coroutineScope.launch {
                                try {
                                    postViewModel.upVote(post.id)
                                    Log.d("PostCard", "like ${post.id}")
                                } catch (e: Exception) {
                                    Log.e("PostCard", "error: ${e.message}")
                                }
                            }
                        },
                        onDislikeClick = {
                            coroutineScope.launch {
                                try {
                                    postViewModel.downVote(post.id)
                                    Log.d("PostCard", "dislike ${post.id}")
                                } catch (e: Exception) {
                                    Log.e("PostCard", "error: ${e.message}")
                                }
                            }
                        },
                        onCommentClick = {},
                        onEditClick = { postId ->
                            postId.let {
                                navController.navigate("postEdit/$it")
                            }
                        }
                    )
                }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}




