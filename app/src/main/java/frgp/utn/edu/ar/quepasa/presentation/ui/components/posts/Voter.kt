package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import kotlinx.coroutines.launch
import java.sql.Timestamp

fun formatNumber(value: Int): String {
    return when {
        value in -999..999 -> value.toString()
        value in -999999..999999 -> {
            val rounded = value / 1000.0
            String.format("%.1f K", rounded).replace(",0", "")
        }
        else -> {
            val rounded = value / 1_000_000.0
            String.format("%.1f M", rounded).replace(",0", "")
        }
    }
}


@Composable
fun Voter(
    voteCount: VoteCount,
    onVote: suspend (Int) -> Unit,
    clickable: Boolean,
    small: Boolean = false,
    transparent: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val m = if(small) Modifier
        .size(36.dp)
        .padding(8.dp) else Modifier
    Card(
        modifier = Modifier.clip(RoundedCornerShape(40.dp)),
        colors = if(transparent) CardDefaults.cardColors(
            containerColor = Color.Transparent
        ) else CardDefaults.cardColors()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            IconToggleButton(
                modifier = m,
                enabled = clickable,
                checked = voteCount.uservote == 1,
                onCheckedChange = { scope.launch { onVote(1) } }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_upward_24),
                    contentDescription = "Up"
                )
            }
            Text(
                text = formatNumber(voteCount.votes)
            )
            IconToggleButton(
                modifier = m,
                enabled = clickable,
                checked = voteCount.uservote == -1,
                onCheckedChange = { scope.launch { onVote(-1) } }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_downward_24),
                    contentDescription = "Down"
                )
            }
        }

    }
}

@Composable
fun CommenterIndicator(
    count: Int,
    onClick: suspend () -> Unit,
    clickable: Boolean
) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier.clip(RoundedCornerShape(40.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 6.dp, end = 16.dp)
        ){
            IconToggleButton(
                enabled = clickable,
                checked = false,
                onCheckedChange = { scope.launch { onClick() } }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_comment_24),
                    contentDescription = "Comment"
                )
            }
            Text(
                text = if(count == 0) "Comentar" else formatNumber(count)
            )
        }

    }
}


@Composable @Preview
fun VoterPreview() {
    val initial = 15999
    var voteCount by remember { mutableStateOf<VoteCount>(VoteCount(
        votes = initial ,
        uservote = 1,
        updated = Timestamp(System.currentTimeMillis())
    )) }
    Box(modifier = Modifier.padding(all = 16.dp)) {
        Voter(
            voteCount = voteCount,
            onVote = {
                voteCount = voteCount.copy(
                    votes = if(voteCount.uservote == it) initial else initial + it,
                    uservote = if(voteCount.uservote == it) 0 else it
                )
            },
            clickable = true,
            small = true,
            transparent = true
        )
    }
}

@Composable @Preview
fun VoterDisabledPreview() {
    val initial = 997
    var voteCount by remember { mutableStateOf<VoteCount>(VoteCount(
        votes = initial + 1,
        uservote = 1,
        updated = Timestamp(System.currentTimeMillis())
    )) }
    Box(modifier = Modifier.padding(all = 16.dp)) {
        Voter(
            voteCount = voteCount,
            onVote = {
                voteCount = voteCount.copy(
                    votes = if(voteCount.uservote == it) initial else initial + it,
                    uservote = if(voteCount.uservote == it) 0 else it
                )
            },
            clickable = false,
            small = true
        )
    }
}

