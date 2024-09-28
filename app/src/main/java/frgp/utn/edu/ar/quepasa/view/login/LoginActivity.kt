package frgp.utn.edu.ar.quepasa.view.login

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.ui.theme.QuePasaAppTheme

class LoginActivity: ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuePasaAppTheme {
                Scaffold(
                    topBar = {},
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding -> LoginPage(modifier = Modifier.padding(innerPadding), loginViewModel) }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier, loginViewModel: LoginViewModel) {

    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Nombre de usuario") },
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Contraseña") },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage(modifier = Modifier, loginViewModel = LoginViewModel(Application()))
}