package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.RepeatPasswordField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.SignupPasswordField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.UsernameField

@AndroidEntryPoint
class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: Sample2ViewModel = hiltViewModel()
            signUpScreen(viewModel)
        }
    }
}

@Composable
fun signUpScreen(viewModel: Sample2ViewModel?) {
    var tab by remember {mutableStateOf(1)}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(TabRowDefaults.primaryContainerColor)
    ) {
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
        if (tab == 0) loginTabContent(viewModel) else signUpTabContent(viewModel)

    }


}

@Composable
fun loginTabContent(viewModel: Sample2ViewModel?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Composable
fun signUpTabContent(viewModel: Sample2ViewModel?) {
    if(viewModel == null) return;
    val username = viewModel.signupUsername.collectAsState()
    var usernameIsValid = viewModel.usernameIsValid.collectAsState()
    var password = viewModel.signupPassword.collectAsState()
    var passwordIsValid = viewModel.passwordIsValid.collectAsState()
    var repeatablePassword = viewModel.signupPasswordRepeatable.collectAsState()
    var repeatablePasswordIsValid = viewModel.passwordRepeatableIsValid.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            UsernameField(
                modifier = Modifier,
                value = "$username",
                validator = viewModel::usernameValidator,
                onChange = viewModel::setLoginUsername,
                onValidityChange =  viewModel::setUsernameValidity
            )
        }
        Row() {
            SignupPasswordField(
                modifier = Modifier,
                value = "$password",
                validator = viewModel::passwordValidator,
                onChange = viewModel::setSignupPassword,
                onValidityChange = viewModel::setPasswordValidity
            )
        }
        Row() {
            RepeatPasswordField(
                modifier = Modifier,
                value = "$repeatablePassword",
                validator = { viewModel.passwordRepeatValidator(it, "$password") },
                onChange = viewModel::setSignupRepeatablePassword,
                onValidityChange = viewModel::setRepeatablePasswordValidity
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
        ) {
            Button(
                enabled = listOf(usernameIsValid, passwordIsValid, repeatablePasswordIsValid).all { it },
                onClick = viewModel::signUp
            ) {
                Text("Registrate")
            }
        }
    }
}

@Preview
@Composable
fun signUpScreenPreview() {
    signUpScreen(
        viewModel = null
    )
}
