package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.screen

import BaseComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalSnack
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.PasswordField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth.UpdatePasswordScreenViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun UpdatePasswordScreen(
    navController: NavHostController,
    viewModel: UpdatePasswordScreenViewModel = hiltViewModel()
) {
    val auth by LocalAuth.current.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val feedback by viewModel.feedback.collectAsState()
    val snack = LocalSnack.current
    val newPassword by viewModel.newPassword.collectAsState()
    val repeatPassword by viewModel.repeatNewPassword.collectAsState()
    val newPasswordIsValid by viewModel.valid_newPassword.collectAsState()
    val repeatPasswordIsValid by viewModel.valid_repeatPassword.collectAsState()
    val ready by remember { derivedStateOf { newPasswordIsValid && repeatPasswordIsValid } }
    val msg by viewModel.s.collectAsState(initial = null)

    val err by viewModel.err.collectAsState()
    if(err != null) {
        LaunchedEffect(err) {
            snack.showSnackbar(
                message = err!!.message,
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )
        }
    }

    LaunchedEffect(msg) {
        if(msg != null) snack.showSnackbar(
            message = msg ?: "",
            withDismissAction = true,
            duration = SnackbarDuration.Long
        )
    }

    if(auth.isEmpty()) return

    BaseComponent(
        navController = navController,
        title = "Cambiar contraseña",
        back = true,/*
        backRoute = navController.backQueue
            .takeIf { it.size > 1 }
            ?.let { it[it.size - 2] }
            ?.destination?.route ?: "advancedProfileSettings"*/
        "updatePassword"
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(!loading) PasswordField(
                label = "Nueva contraseña",
                value = newPassword,
                validator = viewModel::newPasswordValidator,
                onChange = viewModel::updateNewPassword,
                serverError = feedback?.of(viewModel.NEWPASSWORD_ID),
                clearServerError = {
                    if(feedback?.field.equals(viewModel.NEWPASSWORD_ID))
                        viewModel.feedback.update { null }
                }
            )
            if(newPasswordIsValid && !loading) PasswordField(
                label = "Repita contraseña",
                value = repeatPassword,
                validator = viewModel::repeatPasswordValidator,
                onChange = viewModel::updateRepeatPassword,
                serverError = feedback?.of(viewModel.REPEATPASSWORD_ID),
                clearServerError = {
                    if(feedback?.field.equals(viewModel.REPEATPASSWORD_ID))
                        viewModel.feedback.update { null }
                }
            )
            if(!loading && ready) Button(
                onClick = {
                    scope.launch { viewModel.request() }
                }
            ) {
                Text("Cambiar contraseña")
            }
            if(loading) CircularProgressIndicator()
        }
    }
}
