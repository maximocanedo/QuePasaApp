package frgp.utn.edu.ar.quepasa.presentation.ui.components.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.commenting.AbsComment
import frgp.utn.edu.ar.quepasa.data.model.commenting.EventComment
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.Voter
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.Avatar
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo
import java.sql.Timestamp
import java.util.UUID

enum class CommentParent {
    EVENT, POST, PICTURE
}

@Composable
fun <T: AbsComment> CommentCard(
    comment: T?,
    parentType: CommentParent,
    vm: CommentCardViewModel = hiltViewModel(key = "comment@${comment?.id?:"null"}")
) {

    val data by vm.comment.collectAsState()
    val votes by vm.votes.collectAsState()
    LaunchedEffect(comment) {
        vm.load(comment, comment?.id)
    }
    Row(
        modifier = Modifier.padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column (
            modifier= Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avatar(
                imageUrl = "http://canedo.com.ar:8080/api/pictures/${data?.author?.picture?.id}/view",
                description = data?.author?.name?:""
            )
        }
        Card(
        ) {
            Column (Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        text = data?.author?.name ?: "",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = " @${data?.author?.username?:""}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if(data?.timestamp != null) {
                        Text(
                            text = " · ${data!!.timestamp.formatTimeAgo()}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(data?.content ?: "")
                }
                votes?.let {
                    Row(

                    ) {
                        Voter(
                            voteCount = votes!!,
                            onVote = {
                                if(it == 1) vm.upvote()
                                else vm.downvote()
                            },
                            clickable = true)
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun CommentCardAbPreview() {
    CommentCard<EventComment>(
        comment = EventComment(
            content = "Lorem ipsum dolor sit amet lkjasflasdjk klasjhflsakdjhfsaldkjh jkhkljsadhjh jahkjhkjh  a hkjahfksdjhfsk",
            id = UUID.randomUUID(),
            author = User(
                name = "Máximo Canedo",
                picture = Picture(
                    id = UUID.fromString("c7d6a327-12f4-4b8d-a81f-2059dd340fe7"),
                    description = "",
                    mediaType = "image/jpeg",
                    uploadedAt = Timestamp(1000000),
                    owner = null
                ),
                id = 338,
                username = "root",
                active = true,
                address = "",
                role = Role.ADMIN,
                neighbourhood = null,
                email = emptySet(),
                phone = emptySet()
            ),
            event = Event(),
            timestamp = Timestamp(System.currentTimeMillis()),
            votes = VoteCount(votes = 154, uservote = 1, updated = Timestamp(System.currentTimeMillis()))
        ),
        parentType = CommentParent.EVENT
    )
}