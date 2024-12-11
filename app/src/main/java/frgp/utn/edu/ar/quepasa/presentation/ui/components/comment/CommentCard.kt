package frgp.utn.edu.ar.quepasa.presentation.ui.components.comment

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.commenting.AbsComment
import frgp.utn.edu.ar.quepasa.data.model.commenting.EventComment
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationContext
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalSnack
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.Voter
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.Avatar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalButton
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.UUID

enum class CommentParent {
    EVENT, POST, PICTURE
}

@Composable
fun <T: AbsComment> CommentCard(
    votes: VoteCount?,
    data: T?,
    onUpvote: suspend () -> Unit,
    onDownvote: suspend () -> Unit,
    onEdit: suspend () -> Unit = {},
    onDelete: suspend () -> Unit = {},
    editable: Boolean = true,
    deletable: Boolean = true
) {
    var showingLastRow by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    data?.let {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .animateContentSize()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column (
                modifier = Modifier
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    data.author?.let {
                        UserHorizontalButton(
                            user = data.author!!,
                            onClick = {},
                            caption = data.timestamp.formatTimeAgo()
                        )
                        if(editable || deletable) {
                            Spacer(Modifier.weight(1f))
                            IconButton(onClick = {
                                showingLastRow = !showingLastRow
                            }) {
                                Icon(
                                    painter = painterResource(id = if(showingLastRow) R.drawable.keyboard_arrow_up else R.drawable.keyboard_arrow_down),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
                Row( modifier = Modifier.padding(horizontal = 12.dp) ) {
                    Text(text = data.content)
                }
                votes?.let {
                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    ) {
                        Voter(
                            voteCount = votes,
                            onVote = {
                                if(it == 1) onUpvote()
                                else onDownvote()
                            },
                            clickable = true,
                            small = true,
                            transparent = true
                        )
                    }
                }
                if(showingLastRow) {
                    if(editable) ListItem(
                        modifier = Modifier.clickable { scope.launch { onEdit() } },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        headlineContent = { Text("Editar") },
                        leadingContent = { Icon(painterResource(id = R.drawable.edit), "") }
                    )
                    if(deletable) ListItem(
                        modifier = Modifier.clickable { scope.launch { onDelete() } },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        headlineContent = { Text("Eliminar") },
                        leadingContent = { Icon(painterResource(id = R.drawable.baseline_delete_24), "") }
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: AbsComment> CommentCard(
    comment: T?,
    vm: CommentCardViewModel = hiltViewModel(key = "comment@${comment?.id?:"null"}")
) {
    val userContext by LocalAuth.current.collectAsState()
    val scope = rememberCoroutineScope()
    val canUpdate: Boolean = userContext.username == comment?.author?.username
    val canDelete: Boolean = canUpdate || userContext.isAdmin
    val data by vm.comment.collectAsState()
    val votes by vm.votes.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val msg by vm.msg.collectAsState(initial = null)
    val fail by vm.fail.collectAsState(initial = null)
    val snack = LocalSnack.current
    var contnt by remember { mutableStateOf(data?.content ?: "") }
    LaunchedEffect(msg) {
        if(msg == null) return@LaunchedEffect
        val s = snack.showSnackbar(msg!!)
    }
    LaunchedEffect(fail) {
        if(fail == null) return@LaunchedEffect
        val s = snack.showSnackbar(fail!!.message)
    }
    LaunchedEffect(comment) {
        vm.load(comment, comment?.id)
    }
    CommentCard(
        votes = votes,
        data = data,
        onUpvote = vm::upvote,
        onDownvote = vm::downvote,
        editable = canUpdate,
        deletable = canDelete,
        onEdit = { showEditDialog = true },
        onDelete = { showDeleteDialog = true }
    )
    if(showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { vm.delete(); showDeleteDialog = false }
                }) {
                    Text("Eliminar")
                }
            },
            title = { Text("¿Estás seguro de eliminar este comentario?") },
            text = { Text("Esta acción no se puede deshacer. ") },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
    if(showEditDialog) {
        ModalBottomSheet(onDismissRequest = { showEditDialog = false }) {
            Row() {
                BasicTextField(
                    value = contnt,
                    onValueChange = { contnt = it }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { showEditDialog = false }) { Text("Cancelar") }
                Button(enabled = !(data?.content == contnt || contnt.isEmpty()),onClick = {
                    scope.launch {

                    }
                }) {
                    Text("Editar")
                }
            }
        }
    }
}

@Preview
@Composable
fun CommentCardAbPreview() {
    CommentCard<EventComment>(
        data = EventComment(
            content = "Lorem ipsum dolor sit amet lkjasflasdjk klasjhflsakdjhfsaldkjh jkhkljsa" +
                    "dhjh jahkjhkjh  a hkjahfksdjhfsk",
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
            votes = VoteCount(
                votes = 154,
                uservote = 1,
                updated = Timestamp(System.currentTimeMillis())
            )
        ),
        votes = VoteCount(
            votes = 139,
            uservote = 0,
            updated = Timestamp(System.currentTimeMillis())
        ),
        onUpvote = {},
        onDownvote = {}
    )
}