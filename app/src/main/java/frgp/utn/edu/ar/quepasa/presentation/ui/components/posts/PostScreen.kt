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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TrendsScreen
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.DialogProperties
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.WarningMessage


import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.derivedStateOf
import frgp.utn.edu.ar.quepasa.data.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    navController: NavHostController,
    selectedTag: String?,
    wrapInBaseComponent: Boolean = false
) {
    val postViewModel: PostViewModel = hiltViewModel()
    val postsState = postViewModel.posts.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val isRefreshing by postViewModel.isRefreshing.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var postToDelete by remember { mutableStateOf<Int?>(null) }
    var currentPage by remember { mutableStateOf(0) }

    val sortedPosts = postsState.value.content
        .filter { post -> selectedTag == null || (post.tags?.contains(selectedTag) ?: false) }
        .sortedWith(compareByDescending<Post> { post -> post.votes?.votes }
            .thenByDescending { post -> post.timestamp })

    val content: @Composable () -> Unit = {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                coroutineScope.launch {
                    postViewModel.refreshPosts()
                    currentPage=0
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

                TrendsScreen(navController)

                sortedPosts.forEach { post ->
                    PostCard(
                        post = post,
                        navController = navController,
                        onLikeClick = {
                            coroutineScope.launch {
                                try {
                                    postViewModel.upVote(post.id)
                                } catch (e: Exception) {
                                    Log.e("PostCard", "Error al dar like: ${e.message}")
                                }
                            }
                        },
                        onDislikeClick = {
                            coroutineScope.launch {
                                try {
                                    postViewModel.downVote(post.id)
                                } catch (e: Exception) {
                                    Log.e("PostCard", "Error al dar dislike: ${e.message}")
                                }
                            }
                        },
                        onCommentClick = {},
                        onEditClick = { postId ->
                            navController.navigate("postEdit/$postId")
                        },
                        onRemoveClick = {
                            postToDelete = post.id
                            showDialog = true
                        },
                    )
                }

                if (currentPage < postsState.value.totalPages - 1) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                currentPage++
                                postViewModel.getPosts(page = currentPage, size = 5, activeOnly = true)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Cargar más")
                    }
                }

                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }

    if (wrapInBaseComponent) {
        val user by LocalAuth.current.collectAsState()
        BaseComponent(navController, user.user, "Posts", false) {
            content()
        }
    } else {
        content()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmar eliminación") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar este post?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            postToDelete?.let { postViewModel.deletePost(it) }
                            postToDelete = null
                            showDialog = false
                        }
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        )
    }
}
