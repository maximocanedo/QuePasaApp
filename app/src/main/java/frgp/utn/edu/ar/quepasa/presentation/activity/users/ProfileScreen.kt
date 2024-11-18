package frgp.utn.edu.ar.quepasa.presentation.activity.users

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.data.dto.request.UserPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.source.remote.saveAuthToken
import frgp.utn.edu.ar.quepasa.domain.context.feedback.FeedbackProvider
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationProvider
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.LoginActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.BasicUserInfoCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.mail.MailsCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.phone.PhonesCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserDisplayDesign
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.users.ProfileScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Timestamp

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
        if(user == null) return@FeedbackProvider
        ProfileScreenContent(
            user!!,
            viewModel::onMailRegistrationRequest,
            viewModel::onMailValidationRequest,
            viewModel::onMailDeleteRequest,
            isRefreshing,
            viewModel::updateUser,
            viewModel::onPhoneRegistrationRequest,
            viewModel::onPhoneValidationRequest,
            viewModel::onPhoneDeleteRequest,
            viewModel::onPatchEditRequest,
            viewModel::onConfirmedDeleteRequest,
            navController
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    user: User,
    onMailRegistration: suspend (String) -> Unit,
    onMailValidationRequest: suspend (Mail, String) -> Boolean,
    onMailDeleteRequest: suspend (Mail) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPhoneRegistration: suspend (String) -> Unit,
    onPhoneValidationRequest: suspend (Phone, String) -> Boolean,
    onPhoneDeleteRequest: suspend (Phone) -> Unit,
    onPatchEditRequest: suspend (UserPatchEditRequest) -> Unit,
    onConfirmedDisableRequest: suspend () -> Unit,
    navController: NavHostController
) {
    val me by LocalAuth.current.collectAsState()
    val context = LocalContext.current
    var showDisableDialog by remember { mutableStateOf(false) }
    val itsMe by remember {
        derivedStateOf {
            me.ok && me.username == (user.username)
        }
    }

    BaseComponent(navController, user, title = "Perfil", back = false) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UserDisplayDesign(user, Modifier.fillMaxWidth())
                BasicUserInfoCard(
                    user = user,
                    modifier = Modifier.fillMaxWidth(),
                    onNameUpdateRequest = {
                        val req = UserPatchEditRequest(name = it)
                        onPatchEditRequest(req)
                    },
                    onAddressUpdateRequest = {
                        val req = UserPatchEditRequest(address = it)
                        onPatchEditRequest(req)
                    }
                )
                if(itsMe) MailsCard(
                    user.email, Modifier.fillMaxWidth(),
                    onMailRegistration, onMailDeleteRequest, onMailValidationRequest
                )
                if(itsMe) PhonesCard(
                    phones = user.phone,
                    Modifier.fillMaxWidth(),
                    onPhoneRegistration,
                    onPhoneDeleteRequest,
                    onPhoneValidationRequest
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { showDisableDialog = true }
                ) {
                    Text("Deshabilitar cuenta")
                }
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }

    if(showDisableDialog) AlertDialog(
        onDismissRequest = { showDisableDialog = false },
        title = {
            Text("¿Seguro de eliminar ${if(itsMe) "tu " else ""}cuenta?")
        },
        text = {
            Text("Esta acción es permanente y no se puede deshacer. ")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showDisableDialog = false
                    CoroutineScope(IO).launch {
                        onConfirmedDisableRequest()
                        saveAuthToken(context, "")
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            ) {
                Text("Deshabilitar")
            }
            TextButton(
                onClick = {
                    showDisableDialog = false
                }
            ) {
                Text("Volver")
            }
        }
    )
}

