package frgp.utn.edu.ar.quepasa.presentation.activity.users

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.dto.request.UserPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.domain.context.feedback.FeedbackProvider
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.BasicUserInfoCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserDisplayDesign
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.users.ProfileScreenViewModel
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, username: String? = null) {
    val cur by LocalAuth.current.collectAsState()
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val feedback by viewModel.feedback.collectAsState()
    FeedbackProvider(feedback) {
        viewModel.username.update { username }
        viewModel.updateUser()
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        val user: User? by viewModel.user.collectAsState()
        if(user == null || (!user!!.active && !cur.isAdmin)) ModalBottomSheet(
            onDismissRequest = { navController.navigate(
                navController.backQueue
                    .takeIf { it.size > 1 }
                    ?.let { it[it.size - 2] }
                    ?.destination?.route ?: "home"
            ) }
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = Center,
                    text = "Este usuario no existe, está deshabilitado, o no tenés permisos para visualizar su información."
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(
                            navController.backQueue
                                .takeIf { it.size > 1 }
                                ?.let { it[it.size - 2] }
                                ?.destination?.route ?: "home"
                        )
                    }
                ) { Text("Volver") }
            }
        }
        else ProfileScreenContent(
            user!!,
            isRefreshing,
            viewModel::updateUser,
            viewModel::onPatchEditRequest,
            navController
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    user: User,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPatchEditRequest: suspend (UserPatchEditRequest) -> Unit,
    navController: NavHostController
) {
    val auth by LocalAuth.current.collectAsState()
    val itsMe by remember {
        derivedStateOf {
            auth.ok && auth.username == (user.username)
        }
    }
    BaseComponent(navController, title = "Perfil", back = false) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
            ) {
                item { UserDisplayDesign(user, Modifier.fillMaxWidth()) }
                item {
                    BasicUserInfoCard(
                        user = user,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        onNameUpdateRequest = {
                            val req = UserPatchEditRequest(name = it)
                            onPatchEditRequest(req)
                        },
                        onAddressUpdateRequest = {
                            val req = UserPatchEditRequest(address = it)
                            onPatchEditRequest(req)
                        },
                        onNeighbourhoodUpdateRequest = {
                            val req = UserPatchEditRequest(neighbourhood = it)
                            onPatchEditRequest(req)
                        }
                    )
                }
                if(itsMe || auth.isAdmin) item {
                    ListItem(
                        headlineContent = { Text("Configuración de la cuenta") },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_forward),
                                contentDescription = "Cuenta"
                            )
                        },
                        modifier = Modifier.clickable {
                            navController.navigate(if(itsMe) "advancedProfileSettings" else "advancedProfileSettings/${user.username}")
                        }
                    )
                }
            }
        }
    }
}

