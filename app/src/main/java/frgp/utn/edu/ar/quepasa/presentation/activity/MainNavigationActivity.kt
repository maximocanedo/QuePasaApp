package frgp.utn.edu.ar.quepasa.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationProvider
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.NavigationMainHost
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.TP4Theme

class MainNavigationActivity : AuthenticatedActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val user by super.authenticatedUser.collectAsState()
            AuthenticationProvider(user) {
                TP4Theme {
                    NavigationMainHost(navController = navController, null)
                }
            }
        }
    }
}