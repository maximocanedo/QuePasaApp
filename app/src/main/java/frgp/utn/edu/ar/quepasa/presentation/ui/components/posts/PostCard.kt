import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.data.model.enums.SubnationalDivisionDenomination
import frgp.utn.edu.ar.quepasa.presentation.ui.components.card.components.CardButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import java.sql.Timestamp
import java.util.concurrent.TimeUnit

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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardButton(
                    description = "Like",
                    icon = R.drawable.baseline_arrow_upward_24,
                    onClick = onLikeClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )

                CardButton(
                    description = "Dislike",
                    icon = R.drawable.baseline_arrow_downward_24,
                    onClick = onDislikeClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                )

                CardButton(
                    description = "Comment",
                    icon = R.drawable.baseline_comment_24,
                    onClick = onCommentClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                )

                if (post.owner?.id == post.id) {
                    CardButton(
                        description = "Edit",
                        icon = R.drawable.edit,
                        onClick = { post.id?.let { onEditClick(it) } },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
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
        owner = frgp.utn.edu.ar.quepasa.data.model.User(
            id = 1,
            username = "patriciobor1",
            name = "Patricio Bordon",
            phone = emptySet(),
            address = "123 Calle Falsa",
            neighbourhood = frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood(
                id = 1,
                name = "General Pacheco",
                city = frgp.utn.edu.ar.quepasa.data.model.geo.City(1, "Buenos Aires", subdivision = frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision(
                    iso3= "",
                    label="",
                    denomination= SubnationalDivisionDenomination.CITY,
                    country = frgp.utn.edu.ar.quepasa.data.model.geo.Country(
                        iso3= "Arg",
                        label= "Argentina",
                        active= true

                    ),
                    active = true
                ), true),
                active = true
            ),
            picture = null,
            email = emptySet(),
            role = frgp.utn.edu.ar.quepasa.data.model.enums.Role.USER,
            active = true
        ),
        title = "Título ejemplo",
        subtype = PostSubtype(1, PostType(1, "PostType ejemplo"), "PostSubtype ejemplo"),
        description = "Este es un ejemplo estoy usando preview como me recomendo Maximo, ahora la pc suena mas fuerte jajjaj. mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto mas texto ",
        timestamp = Timestamp(System.currentTimeMillis()),
        neighbourhood = frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood(
            id = 1,
            name = "General Pacheco",
            city = frgp.utn.edu.ar.quepasa.data.model.geo.City(1, "Buenos Aires", subdivision = frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision(
                iso3= "",
                label="",
                denomination= SubnationalDivisionDenomination.CITY,
                country = frgp.utn.edu.ar.quepasa.data.model.geo.Country(
                    iso3= "Arg",
                    label= "Argentina",
                    active= true

                ),
                active = true
            ), true),
            active = true
        ),        isActive = true,
        votes = frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount(10, 1, Timestamp(System.currentTimeMillis()))
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
