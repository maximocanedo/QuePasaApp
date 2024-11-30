package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.screen

import BaseComponent
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.source.remote.saveAuthToken
import frgp.utn.edu.ar.quepasa.domain.context.feedback.FeedbackProvider
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalSnack
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.LoginActivity
import frgp.utn.edu.ar.quepasa.presentation.activity.users.ProfileScreenContent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.mail.MailsCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.phone.PhonesCard
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.users.ProfileScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun AdvancedUserSettingsScreen(navController: NavHostController, username: String? = null) {
    val cur by LocalAuth.current.collectAsState()
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val feedback by viewModel.feedback.collectAsState()
    FeedbackProvider(feedback) {
        viewModel.username.update { username }
        viewModel.updateUser()
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        val user: User? by viewModel.user.collectAsState()
        if(user == null) return@FeedbackProvider
        AdvancedUserSettings(
            navController = navController,
            user = user!!,
            viewModel::onMailRegistrationRequest,
            viewModel::onMailValidationRequest,
            viewModel::onMailDeleteRequest,
            viewModel::onPhoneRegistrationRequest,
            viewModel::onPhoneValidationRequest,
            viewModel::onPhoneDeleteRequest,
            viewModel::onConfirmedDeleteRequest,
            isRefreshing,
            viewModel::updateUser
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedUserSettings(
    navController: NavHostController,
    user: User,
    onMailRegistration: suspend (String) -> Unit,
    onMailValidationRequest: suspend (Mail, String) -> Boolean,
    onMailDeleteRequest: suspend (Mail) -> Unit,
    onPhoneRegistration: suspend (String) -> Unit,
    onPhoneValidationRequest: suspend (Phone, String) -> Boolean,
    onPhoneDeleteRequest: suspend (Phone) -> Unit,
    onConfirmedDisableRequest: suspend () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    val context = LocalContext.current
    val snack = LocalSnack.current
    var showDisableDialog by remember { mutableStateOf(false) }
    val auth by LocalAuth.current.collectAsState()
    val itsMe by remember {
        derivedStateOf {
            auth.ok && auth.username == (user.username)
        }
    }
    BaseComponent(
        navController = navController,
        title = "Cuenta",
        back = true,
        /*backRoute = navController.backQueue
            .takeIf { it.size > 1 }
            ?.let { it[it.size - 2] }
            ?.destination?.route ?: "userProfile",*/
        "advancedProfileSettings"
    ){
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        ) {
            LazyColumn {
                if (itsMe) item {
                    MailsCard(
                        user.email, Modifier.fillMaxWidth(),
                        onMailRegistration, onMailDeleteRequest, onMailValidationRequest
                    )
                    HorizontalDivider(Modifier.fillMaxWidth())
                }
                if (itsMe) item {
                    PhonesCard(
                        phones = user.phone,
                        Modifier.fillMaxWidth(),
                        onPhoneRegistration,
                        onPhoneDeleteRequest,
                        onPhoneValidationRequest
                    )
                    HorizontalDivider(Modifier.fillMaxWidth())
                }
                if(itsMe) item {
                    ListItem(
                        modifier = Modifier.clickable {
                            navController.navigate("totpSettings")
                        },
                        headlineContent = {
                            Text("Autenticación en dos pasos")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.security),
                                contentDescription = "Ajustes de TOTP"
                            )
                        }
                    )
                }
                if(itsMe) item {
                    ListItem(
                        modifier = Modifier.clickable {
                            navController.navigate("updatePassword")
                        },
                        headlineContent = {
                            Text("Cambiar contraseña")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.password),
                                contentDescription = "Contraseña"
                            )
                        }
                    )
                }

                if(auth.isAdmin || itsMe) item {
                    HorizontalDivider(Modifier.fillMaxWidth())
                    ListItem(
                        modifier = Modifier.clickable {
                            showDisableDialog = true
                        },
                        headlineContent = {
                            Text("Desactivar cuenta")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.delete_forever),
                                contentDescription = "Desactivar"
                            )
                        }
                    )
                }
            }
        }
    }
    if(showDisableDialog) AlertDialog(
        onDismissRequest = { showDisableDialog = false },
        title = {
            Text("¿Seguro de eliminar ${ if(itsMe) "tu" else "esta" } cuenta?")
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
                        if(itsMe) {
                            saveAuthToken(context, "")
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            snack.showSnackbar(
                                message = "La cuenta fue deshabilitada. "
                            )
                            navController.navigate("home")
                        }
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