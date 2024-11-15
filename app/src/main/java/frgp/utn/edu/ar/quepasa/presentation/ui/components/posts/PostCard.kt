import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.model.enums.SubnationalDivisionDenomination
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import okhttp3.internal.userAgent
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow

fun formatNumber(number: Int): String {
    if (number < 1000) return "$number"
    val exp = (ln(abs(number.toDouble())) / ln(1000.0)).toInt()
    val suffix = "KMBT"[exp - 1]
    return String.format("%.1f%s", number / 1000.0.pow(exp.toDouble()), suffix)
}

fun Timestamp.formatTimeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - this.time

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "justo ahora"
        diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)}m atrás"
        diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)}h atrás"
        diff < TimeUnit.DAYS.toMillis(7) -> "${TimeUnit.MILLISECONDS.toDays(diff)}d atrás"
        else -> "hace más de una semana"
    }
}

@Composable
fun PostCard(
    post: Post,
    user: User? = null,
    onLikeClick: () -> Unit,
    onDislikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onEditClick: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
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
                    post.timestamp?.let {
                        Text(
                            text = it.formatTimeAgo(),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ReadMoreText(
                text = post.description ?: "Sin descripción",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 14.sp
                ),
                minLines = 3,
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                if (user != null && post.owner?.id == user.id) {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { post.id?.let { onEditClick(it) } }
                    )
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
            role = frgp.utn.edu.ar.quepasa.data.model.enums.Role.USER,
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
        PostCard(
            post = examplePost,
            onLikeClick = {},
            onDislikeClick = {},
            onCommentClick = {},
            onEditClick = { postId -> {} }
        )
    }
}
