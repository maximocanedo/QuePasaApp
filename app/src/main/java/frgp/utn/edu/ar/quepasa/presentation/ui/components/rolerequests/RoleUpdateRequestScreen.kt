package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.DocumentFileField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.DocumentNationalField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.NameField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.RoleRequesterField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.request.RoleUpdateRequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import quepasa.api.validators.commons.StringValidator

@Composable
fun RoleUpdateRequestScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: RoleUpdateRequestViewModel = hiltViewModel()
    val user by LocalAuth.current.collectAsState()
    var dni by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    BaseComponent(navController, null, "Solicitud de rol", true) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NameField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = user.name
            )
            DocumentNationalField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = dni,
                validator = { StringValidator(dni)
                    .isNotBlank()
                    .matches("^\\d+$", "Solo se pueden ingresar números.")
                },
                onChange = { newDni -> dni = newDni },
                onValidityChange = { status -> viewModel.toggleValidationField(0, status)}
            )
            RoleRequesterField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onItemSelected = { role = it },
                onValidityChange = { status -> viewModel.toggleValidationField(1, status) }
            )
            DocumentFileField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = "",
                onIconClick = {}
            )
            Button(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                onClick = {
                    CoroutineScope(IO).launch {
                        val validation = viewModel.checkValidationFields()
                        if(validation) {
                            //val result = viewModel.createRoleRequest(Role.NEIGHBOUR, dni)
                        }
                        else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Tiene campos sin completar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            ) {
                Text("Solicitar")
            }
        }
    }
}

@Preview
@Composable
fun RoleUpdateRequestScreenPreview() {
    val navController = rememberNavController()
    RoleUpdateRequestScreen(navController = navController)
}