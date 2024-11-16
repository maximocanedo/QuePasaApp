import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import frgp.utn.edu.ar.quepasa.utils.date.formatNumber
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo
import java.sql.Timestamp

@Composable
fun PostCard(
    post: Post,
    navController: NavHostController,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    onRemoveClick: (Int) -> Unit,

    ) {
    val user by LocalAuth.current.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = {
            navController.navigate("postDetailedScreen/${post.id}")
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                post.owner?.let {
                    UserHorizontalDesign(
                        user = it,
                        modifier = Modifier
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    post.neighbourhood?.name?.let { neighbourhoodName ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.location),
                                contentDescription = "Ubicación",
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
                    Text(
                        text = post.timestamp.formatTimeAgo(),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ReadMoreText(
                text = post.description,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 14.sp
                ),
                minLines = 3,
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(8.dp))

            if(user.user?.role != Role.USER) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onLikeClick)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_upward_24),
                            contentDescription = "Like",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = formatNumber(post.votes?.votes ?: 0),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onDislikeClick)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_downward_24),
                            contentDescription = "Dislike",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onCommentClick)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_comment_24),
                            contentDescription = "Comment",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (post.owner?.id == user.id || user.user?.role == Role.ADMIN) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onEditClick(post.id) }
                        )
                    }
                    if (post.owner?.id == user.id || user.user?.role == Role.ADMIN) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_24),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onRemoveClick(post.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPostCard() {
    val examplePost = Post(
        id = 1,
        owner = User(
            id = 1,
            username = "patriciobor1",
            name = "Patricio Bordon",
            phone = emptySet(),
            address = "123 Calle Falsa",
            neighbourhood = null,
            picture = null,
            email = emptySet(),
            role = Role.USER,
            active = true
        ),
        title = "Título ejemplo",
        subtype = PostSubtype(1, PostType(1, "PostType ejemplo"), "PostSubtype ejemplo"),
        description = "Este es un ejemplo de cómo debería verse un post con una descripción más larga para probar el diseño.",
        timestamp = Timestamp(System.currentTimeMillis()),
        neighbourhood = null,
        isActive = true,
        votes = frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount(12500, 300, Timestamp(System.currentTimeMillis()))
    )

    MaterialTheme {
        val navController = rememberNavController()
        PostCard(
            post = examplePost,
            navController = navController,
            onLikeClick = {},
            onDislikeClick = {},
            onCommentClick = {},
            onEditClick = { postId -> {} },
            onRemoveClick={}
        )
    }
}
