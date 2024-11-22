package frgp.utn.edu.ar.quepasa.presentation.ui.components.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.commenting.PostComment
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.CardButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components.UpVoteButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.text.ReadMoreText
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalDesign
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo

@Composable
fun PostCommentCard(
    comment: PostComment,
    voteCount: VoteCount?,
    user: User,
    onUpvoteClick: () -> Unit,
    onDownvoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            comment.author?.let {
                UserHorizontalDesign(
                    user = it,
                )
            }
            Text(
                text = comment.timestamp.formatTimeAgo(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(2.dp, end = 8.dp),
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
            minLines = 1,
            maxLines = 4,
        )
        if(user.role != Role.USER) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, end = 8.dp, start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                UpVoteButton(
                    onClick = {
                        onUpvoteClick()
                    },
                    text = voteCount?.votes.toString(),
                )
            }
            Column {
                CardButton(
                    description = "Downvote button",
                    icon = R.drawable.baseline_arrow_downward_24,
                    onClick = {
                        onDownvoteClick()
                    }
                )
            }
            if (user.id == comment.author?.id) {
                Column {
                    CardButton(
                        description = "",
                        icon = R.drawable.baseline_delete_24,
                        onClick = {
                            onDeleteClick()
                        }
                    )
                }
                Column {
                    CardButton(
                        description = "",
                        icon = R.drawable.baseline_edit_document_24,
                        onClick = {
                            onEditClick()
                        }
                    )
                }
            }
        }
        }
    }
}