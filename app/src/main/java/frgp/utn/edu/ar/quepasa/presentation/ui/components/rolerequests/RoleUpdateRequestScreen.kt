package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.DocumentFileField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.DocumentNationalField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields.NameField

@Composable
fun RoleUpdateRequestScreen(navController: NavHostController) {
    val user by LocalAuth.current.collectAsState()
    var dni by remember { mutableStateOf("") }

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
                onChange = { newDni -> dni = newDni },
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
                onClick = { /*TODO: Not implemented yet*/ }
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