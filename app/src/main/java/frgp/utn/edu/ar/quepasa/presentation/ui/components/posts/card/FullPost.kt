import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.LoadMoreButton
import frgp.utn.edu.ar.quepasa.presentation.ui.components.comment.CommentCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.comment.CommentParent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.SF
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.CommenterIndicator
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.Voter
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.card.IndividualPostViewModel
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalButton
import frgp.utn.edu.ar.quepasa.utils.date.formatTimeAgo
import kotlinx.coroutines.CoroutineScope
import java.sql.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPost(
    postId: Int,
    navController: NavHostController,
    onEditClick: (Int) -> Unit,
    onRemoveClick: (Int) -> Unit,
    vm: IndividualPostViewModel = hiltViewModel(key = "post@${postId}")
) {
    val user by LocalAuth.current.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var showDel by remember { mutableStateOf(false) }
    val p by vm.post.collectAsState()
    val votes by vm.votes.collectAsState()
    val cl by vm.cl.collectAsState()
    val comments = remember { vm.comments }

    LaunchedEffect(postId) {
        vm.load(null, postId)
    }
    if(p == null) return

    if (user.user?.role != Role.NEIGHBOUR || !(p!!.audience.name == "NEIGHBORHOOD" &&
                p!!.neighbourhood?.name != user.user?.neighbourhood?.name
                    )
        ) {
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    val rm = Modifier.fillMaxWidth()
                    Spacer(Modifier.height(4.dp))
                    Row(rm, horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            p!!.owner?.let {
                                UserHorizontalButton(user = p!!.owner!!, onClick = {

                                }, caption = p!!.timestamp.formatTimeAgo())
                            }
                        }
                        Column(Modifier.height(64.dp), verticalArrangement = Arrangement.Center) {
                            IconButton(
                                onClick = {
                                    showMenu = true
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more_vert),
                                    contentDescription = "Menú"
                                )
                            }
                        }
                    }
                    Row(
                        rm
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = p!!.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        rm
                            .padding(horizontal = 24.dp)
                            .padding(top = 4.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = p!!.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    LazyRow(
                        rm.padding(horizontal = 24.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        p!!.neighbourhood?.let {
                            item {
                                InputChip(
                                    selected = true,
                                    onClick = { },
                                    label = { Text(p!!.neighbourhood!!.name) },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.location_on),
                                            contentDescription = "Barrio"
                                        )
                                    }
                                )
                            }
                        }
                        item {
                            InputChip(
                                selected = true,
                                onClick = { },
                                label = { Text(p!!.subtype.description) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.tag),
                                        contentDescription = "Subtipo"
                                    )
                                }
                            )
                        }
                    }
                    Row(
                        modifier = rm
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 16.dp, top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        votes?.let {
                            Voter(
                                voteCount = votes!!,
                                onVote = { if (it == 1) vm.upvote() else vm.downvote() },
                                clickable = true
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))

                }
            }
            items(comments) {
                CommentCard(comment = it)
            }
            item {
                LoadMoreButton(
                    loading = cl,
                    onLoadRequest = { vm.loadComments() }
                )
            }
        }
    }
    if(showMenu) ModalBottomSheet(onDismissRequest = { showMenu = false }) {
        ListItem(
            colors = ListItemDefaults.colors(containerColor = BottomSheetDefaults.ContainerColor),
            leadingContent = { Icon(painterResource(R.drawable.edit), "Editar") },
            headlineContent = { Text("Editar") },
            modifier = Modifier.clickable { onEditClick(p!!.id) }
        )
        ListItem(
            colors = ListItemDefaults.colors(containerColor = BottomSheetDefaults.ContainerColor),
            leadingContent = { Icon(painterResource(R.drawable.baseline_delete_24), "Eliminar") },
            headlineContent = { Text("Eliminar") },
            modifier = Modifier.clickable { showDel = true }
        )
    }
    if(showDel) AlertDialog(
        onDismissRequest = { showDel = false },
        confirmButton = { TextButton(onClick = { onRemoveClick(p!!.id) }) { Text("Eliminar") } },
        title = { Text("¿Estás seguro de eliminar esta publicación?")}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFullPost() {
    val initial = 159
    var voteCount by remember { mutableStateOf<VoteCount>(
            VoteCount(
                votes = initial + 1,
                uservote = 1,
                updated = Timestamp(System.currentTimeMillis())
            )
        )
    }
    val examplePost = Post(
        id = 1,
        owner = User(
            id = 1,
            username = "munsanfer",
            name = "Municipo de San Fernando",
            phone = emptySet(),
            address = "123 Calle Falsa",
            neighbourhood = null,
            picture = null,
            email = emptySet(),
            role = Role.USER,
            active = true
        ),
        title = "Impuesto al Pomberito",
        subtype = PostSubtype(1, PostType(1, "Deportes"), "Seguridad"),
        description = "Debido al último avistamiento de este ser, el municipio anunció un nuevo impuesto del 25% en compras de caramelos media hora para costear el Escuadrón Anti Pomberos, que incluirá desde personal entrenado, patrulleros y helicópteros.",
        timestamp = Timestamp(System.currentTimeMillis()),
        neighbourhood = Neighbourhood(
            id = 4,
            name = "Carupá",
            city = SF,
            active = true
        ),
        isActive = true,
        votes = voteCount
    )

    MaterialTheme {
        val navController = rememberNavController()
        Box(modifier = Modifier.padding(8.dp)) {
            FullPost(
                postId = examplePost.id,
                navController = navController,
                onEditClick = { postId -> {} },
                onRemoveClick={}
            )
        }
    }
}
