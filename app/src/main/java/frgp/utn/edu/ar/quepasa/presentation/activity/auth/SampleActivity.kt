package frgp.utn.edu.ar.quepasa.presentation.activity.auth

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.NameField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.OtpTextField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.RepeatPasswordField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.SignupPasswordField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.UsernameField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.validators.users.PasswordValidator
import quepasa.api.validators.users.UsernameValidator

@AndroidEntryPoint
class SampleActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var forceRecompose by remember { mutableStateOf(false) }
            val viewModel: Sample2ViewModel = hiltViewModel()
            val scope = rememberCoroutineScope()
            val rt = viewModel.requiresTotp.collectAsState()
            val totp = viewModel.totpCode.collectAsState()
            val snackbarHostState = remember { SnackbarHostState() }
            LaunchedEffect(1) {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
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
fun SignUpScreen(viewModel: Sample2ViewModel?, modifier: Modifier) {
    var tab by remember {mutableStateOf(0)}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(TabRowDefaults.primaryContainerColor)
    ) {
        Row(modifier = Modifier) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)) {

            }
        }
        TabRow(selectedTabIndex = tab) {
            Tab(
                selected = tab == 0,
                onClick = { tab = 0 },
                text = { Text("Iniciar sesión") }
            )
            Tab(
                selected = tab == 1,
                onClick = { tab = 1 },
                text = { Text("Registrate") }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        if (tab == 0) LoginTabContent(viewModel) else SignUpTabContent(viewModel)

    }


}

@Composable
fun LoginTabContent(viewModel: Sample2ViewModel?) {
    if(viewModel == null) return;
    val username by viewModel.loginUsername.collectAsState()
    val password by viewModel.loginPassword.collectAsState()
    val field by viewModel.serverFeedbackField.collectAsState()
    val rt by viewModel.requiresTotp.collectAsState()
    val totp by viewModel.totpCode.collectAsState()
    val feedback by viewModel.serverFeedback.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            UsernameField(
                modifier = Modifier,
                value = username,
                validator = { UsernameValidator(it).isNotBlank() },
                onChange = viewModel::setLoginUsername,
                onValidityChange = {},
                serverError = null,
                clearServerError = {}
            )
        }
        Row() {
            SignupPasswordField(
                modifier = Modifier,
                value = password,
                validator = { PasswordValidator(it).isNotBlank() },
                onChange = viewModel::setLoginPassword,
                onValidityChange = {},
                serverError = null,
                clearServerError = {}
            )
        }/**
if (rt.value) {
Row {
OutlinedTextField(
value = totp.value,
onValueChange = viewModel::setTotpCode,
label = { Text("Ingrese código TOTP") },
isError = field.value == "totp",
supportingText = { if(field.value == "totp") Text(feedback.value) }
)
}
}*/
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
            OutlinedButton(
                onClick = {
                    viewModel.resetLogin()
                }
            ) {
                Text("Borrar")
            }
        }

    }
}

@Composable
fun SignUpTabContent(viewModel: Sample2ViewModel?) {
    if(viewModel == null) return;
    val feedback by viewModel.serverFeedback.collectAsState()
    val field by viewModel.serverFeedbackField.collectAsState()
    val username by viewModel.signupUsername.collectAsState()
    val name by viewModel.signupName.collectAsState()
    val password by viewModel.signupPassword.collectAsState()
    val repeatablePassword = viewModel.signupPasswordRepeatable.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            NameField(
                modifier = Modifier,
                value = name,
                validator = viewModel::nameValidator,
                onChange = viewModel::setSignupName,
                onValidityChange = viewModel::setNameValidity,
                serverError = if(field == "name") feedback else null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Row() {
            UsernameField(
                modifier = Modifier,
                value = username,
                validator = viewModel::usernameValidator,
                onChange = viewModel::setSignupUsername,
                onValidityChange =  viewModel::setUsernameValidity,
                serverError = if(field == "username") feedback else null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Row() {
            SignupPasswordField(
                modifier = Modifier,
                value = password,
                validator = viewModel::passwordValidator,
                onChange = viewModel::setSignupPassword,
                onValidityChange = viewModel::setPasswordValidity,
                serverError = if(field == "password") feedback else null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Row() {
            RepeatPasswordField(
                modifier = Modifier,
                value = repeatablePassword.value,
                validator = { viewModel.passwordRepeatValidator(it, password) },
                onChange = viewModel::setSignupRepeatablePassword,
                onValidityChange = viewModel::setRepeatablePasswordValidity,
                serverError = null,
                clearServerError = viewModel::clearFeedback
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
        ) {
            Button(
                enabled = true, // listOf(usernameIsValid, passwordIsValid, repeatablePasswordIsValid).all { it.value },
                onClick = {
                    CoroutineScope(IO).launch {
                        viewModel.signUp()
                    }
                }
            ) {
                Text("Registrate")
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

