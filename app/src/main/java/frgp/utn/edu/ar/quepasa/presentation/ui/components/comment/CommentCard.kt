package frgp.utn.edu.ar.quepasa.presentation.ui.components.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.CardButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.UpVoteButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import java.sql.Timestamp

@Composable
fun CommentCard(
    comment: Comment,
    onReplyClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        comment.author?.let {
            UserHorizontalDesign(
                user = it,
            )
        }
        HorizontalDivider(
            thickness = 2.dp
        )
        ReadMoreText(
            text = comment.content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            minLines = 2,
            maxLines = 4,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                UpVoteButton(
                    onClick = {},
                    text = "1",//Contador de likes
                )
            }
            Column {
                CardButton(
                    modifier = Modifier.padding(end = 8.dp),
                    description = "",
                    icon = R.drawable.baseline_arrow_downward_24,
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun CommentCardPreview() {
    val user = User(
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
    )
    val comment = Comment(
        id = 1,
        content = "This is a comment jakbnsdjkasdjkbasjkdjbasjkdbasjkd",
        author = user,
        timestamp = Timestamp.valueOf("2021-10-10 10:10:10"),
        active = true
    )
    CommentCard(
        comment = comment,
        onReplyClick = {}
    )
}