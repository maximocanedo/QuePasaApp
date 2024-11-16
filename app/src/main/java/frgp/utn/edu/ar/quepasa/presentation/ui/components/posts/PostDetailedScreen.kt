package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.images.ImagesListPreview
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import frgp.utn.edu.ar.quepasa.utils.date.formatNumber
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo
import kotlinx.coroutines.launch

@Composable
fun PostDetailedScreen(
    navController: NavHostController,
    postId: Int,
) {
    val user by LocalAuth.current.collectAsState()

    val viewModel: PostViewModel = hiltViewModel()
    val postPictureViewModel: PostPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()

    val post by viewModel.post.collectAsState()
    val votes by viewModel.votes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPostById(postId)
        postPictureViewModel.getPicturesByPost(postId, 0, 10)
        pictureViewModel.setPicturesBitmap(postPictureViewModel.picturesId.value)
    }

    val bitmaps = pictureViewModel.pictures.collectAsState()

    if (post != null) {
        BaseComponent(
            navController = navController,
            user = user.user,
            title = "Publicación",
            back = true
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row {
                            Column {
                                Text(text = post!!.title, fontSize = 24.sp)

                                post!!.neighbourhood?.name?.let { neighbourhoodName ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(R.drawable.location),
                                            contentDescription = "Location",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = neighbourhoodName,
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Post Time Ago",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = post!!.timestamp.formatTimeAgo(),
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        post!!.owner?.let {
                            UserHorizontalDesign(
                                user = it,
                                modifier = Modifier
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = post!!.description, textAlign = TextAlign.Center)
                        }

                        ImagesListPreview(bitmaps = bitmaps.value)

                        Spacer(modifier = Modifier.height(8.dp))

                        if(user.user?.role != Role.USER) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(

                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable(onClick = {
                                        coroutineScope.launch {
                                            try {
                                                viewModel.upVote(post!!.id)
                                                Log.d("PostCard", "like ${post!!.id}")
                                            } catch (e: Exception) {
                                                Log.e("PostCard", "error: ${e.message}")
                                            }
                                        }
                                    })
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_arrow_upward_24),
                                        contentDescription = "Like",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = formatNumber(
                                            votes?.votes ?: post!!.votes?.votes ?: 0
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable(onClick = {
                                        coroutineScope.launch {
                                            try {
                                                viewModel.downVote(post!!.id)
                                                Log.d("PostCard", "dislike ${post!!.id}")
                                            } catch (e: Exception) {
                                                Log.e("PostCard", "error: ${e.message}")
                                            }
                                        }
                                    })
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_arrow_downward_24),
                                        contentDescription = "Dislike",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                if (post!!.owner?.id == user.id || user.user?.role == Role.ADMIN) {
                                    Icon(
                                        painter = painterResource(R.drawable.edit),
                                        contentDescription = "Edit",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable { ->
                                                post!!.id.let {
                                                    navController.navigate("postEdit/$it")
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
                Row {
                    /* TODO Comentarios */
                }
            }
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