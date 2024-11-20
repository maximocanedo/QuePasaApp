package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationProvider
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.basic.ConfSwitch

@Composable
fun TotpSettingsScreen(
    navController: NavHostController? = null
) {
    val auth by LocalAuth.current.collectAsState()
    if(auth.isEmpty()) return
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConfSwitch(
            modifier = Modifier,
            label = "Usar TOTP para iniciar sesión",
            checked = auth.user!!.totp,
            onCheckedChange = {

            }
        )
    }
}

@Preview @Composable
fun TotpSettingsScreenPreview() {
    AuthenticationProvider(User(
        id = 1, name = "Max", username = "max", role = Role.ADMIN, email = emptySet(),
        phone = emptySet(), totp = true, active = true, picture = null, address = "", neighbourhood = null
    )) {
        TotpSettingsScreen()
    }
}