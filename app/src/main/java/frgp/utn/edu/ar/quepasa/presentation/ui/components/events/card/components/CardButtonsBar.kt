package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import java.util.UUID

@Composable
fun CardButtonsBar(eventId: UUID, voteCount: VoteCount) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 4.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            CardButton(
                "asistir",
                R.drawable.baseline_back_hand_24,
                onClick = {}
            )
        }
        Column {
            CardButton(
                "comentar",
                R.drawable.baseline_comment_24,
                onClick = {}
            )
        }
        Column {
            CardButton(
                "editar",
                R.drawable.baseline_edit_document_24,
                onClick = {}
            )
        }
        Column {
            UpVoteButton(
                voteCount.votes.toString(),
                onClick = {}
            )
        }
        Column {
            CardButton(
                "downvote",
                R.drawable.baseline_arrow_downward_24,
                onClick = {}
            )
        }
    }
}