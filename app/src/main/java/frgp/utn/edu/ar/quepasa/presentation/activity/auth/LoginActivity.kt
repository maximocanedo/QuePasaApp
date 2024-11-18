package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.MainActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.NameField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.OtpTextField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.PasswordField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.UsernameField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.Avatar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserDisplayDesign
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.validators.users.PasswordValidator
import quepasa.api.validators.users.UsernameValidator

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var forceRecompose by remember { mutableStateOf(false) }
            val viewModel: LoginViewModel = hiltViewModel()
            val loggedIn by viewModel.loggedIn.collectAsState()
            if(loggedIn) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            val scope = rememberCoroutineScope()
            val rt = viewModel.requiresTotp.collectAsState()
            val totp = viewModel.totpCode.collectAsState()
            val snackbarHostState = remember { SnackbarHostState() }
            LaunchedEffect(1) {
                lifecycleScope.launch {
                    viewModel.checkLoggedInUser()
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.checkLoggedInUser()
                        viewModel.snack.collect {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    }
                }
            }
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                floatingActionButton = {
                }
            ) { contentPadding ->
                SignUpScreen(viewModel, Modifier.padding(contentPadding))

            }
            if(rt.value) ModalBottomSheet(onDismissRequest = {
                forceRecompose = !forceRecompose
                viewModel.resetLogin()
            }) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Código TOTP",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OtpTextField(
                        otpText = totp.value,
                        isError = !viewModel.totpValid.collectAsState().value,
                        onOtpTextChange = { value, otpInputFilled ->
                            viewModel.setTotpCode(value)
                            if(value.length == 6) CoroutineScope(IO).launch {
                                viewModel.login()
                            }
                        },
                        onClearError = {

                        })
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.padding(24.dp, 0.dp),
                        text = "Ingresá el código de seis dígitos generado por tu aplicación de autenticación. ",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))

                }
            }
        }
    }
}

@Composable
fun AlreadyLoggedInUser(viewModel: LoginViewModel?, modifier: Modifier, loginTab: () -> Unit) {
    if (viewModel == null) return
    val user by viewModel.userLogged.collectAsState()
    if (user == null) return

    if (!user!!.active) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    UserDisplayDesign(user = user!!)
                    Text(
                        text = "Tu cuenta se encuentra inhabilitada, contacta a soporte.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.updateLoggedInState(false)
                        loginTab()
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Entendido")
                }
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            UserDisplayDesign(user = user!!)
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.updateLoggedInState(true)
                    }
                ) {
                    Text("Continuar")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                TextButton(
                    onClick = {
                        loginTab()
                        viewModel.logout()
                    }
                ) {
                    Text("No soy yo")
                }
            }
        }
    }
}


@Composable
fun SignUpScreen(viewModel: LoginViewModel?, modifier: Modifier) {
    if(viewModel == null) return
    val user by viewModel.userLogged.collectAsState()
    var tab by remember { mutableStateOf(0) } // (2) } //
    if(user != null) tab = 2
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(TabRowDefaults.primaryContainerColor)
    ) {
        if (tab == 0) LoginTabContent(viewModel, {tab = 1})
        else if (tab == 1) SignUpTabContent(viewModel, { tab = 0 })
        else AlreadyLoggedInUser(viewModel = viewModel, modifier = Modifier, loginTab = { tab = 0 })

    }


}

@Composable
fun LoginTabContent(viewModel: LoginViewModel?, signupTab: () -> Unit) {
    if(viewModel == null) return;
    val username by viewModel.loginUsername.collectAsState()
    val password by viewModel.loginPassword.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.displaySmall
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row() {
            UsernameField(
                modifier = Modifier,
                value = username,
                validator = { UsernameValidator(it).isNotBlank() },
                onChange = viewModel::setLoginUsername,
                serverError = null
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            PasswordField(
                modifier = Modifier,
                value = password,
                validator = { PasswordValidator(it).isNotBlank() },
                onChange = viewModel::setLoginPassword,
                serverError = null,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
        ) {
            Button(
                onClick = {
                    CoroutineScope(IO).launch {
                        viewModel.login()
                    }
                }
            ) {
                Text("Ingresar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
        ) {
            TextButton(
                onClick = {
                    signupTab()
                }
            ) {
                Text("Registrate")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun SignUpTabContent(viewModel: LoginViewModel?, loginTab: () -> Unit) {
    if(viewModel == null) return;
    val feedback by viewModel.serverFeedback.collectAsState()
    val field by viewModel.serverFeedbackField.collectAsState()
    val username by viewModel.signupUsername.collectAsState()
    val name by viewModel.signupName.collectAsState()
    val password by viewModel.signupPassword.collectAsState()
    val repeatablePassword = viewModel.signupPasswordRepeatable.collectAsState()

    val nameIsValid by viewModel.nameIsValid.collectAsState()
    val usernameIsValid by viewModel.usernameIsValid.collectAsState()
    val passwordIsValid by viewModel.passwordIsValid.collectAsState()
    val repeatablePasswordIsValid by viewModel.passwordRepeatableIsValid.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            Text(
                text = "Creá una cuenta",
                style = MaterialTheme.typography.displaySmall
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row() {
            NameField(
                modifier = Modifier,
                value = name,
                validator = viewModel::nameValidator,
                onChange = viewModel::setSignupName,
                serverError = if(field == "name") feedback else null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row() {
            UsernameField(
                modifier = Modifier,
                value = username,
                validator = viewModel::usernameValidator,
                onChange = viewModel::setSignupUsername,
                serverError = if(field == "username") feedback else null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row() {
            PasswordField(
                modifier = Modifier,
                value = password,
                validator = viewModel::passwordValidator,
                onChange = viewModel::setSignupPassword,
                serverError = if(field == "password") feedback else null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row() {
            PasswordField(
                modifier = Modifier,
                value = repeatablePassword.value,
                validator = { viewModel.passwordRepeatValidator(it, password) },
                onChange = viewModel::setSignupRepeatablePassword,
                serverError = null,
                clearServerError = viewModel::clearFeedback,
                label = "Repita la contraseña"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
        ) {
            Button(
                enabled = listOf(nameIsValid, usernameIsValid, passwordIsValid, repeatablePasswordIsValid).all { it },
                onClick = {
                    CoroutineScope(IO).launch {
                        viewModel.signUp()
                    }
                }
            ) {
                Text("Registrate")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
        ) {
            TextButton(
                onClick = {
                    loginTab()
                }
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        viewModel = null,
        modifier = Modifier
    )
}

