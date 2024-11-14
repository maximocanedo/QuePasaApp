package frgp.utn.edu.ar.quepasa.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.NavigationMainHost
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.TP4Theme

class MainNavigationActivity : AuthenticatedActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val user = remember { mutableStateOf<User?>(null) }
            LaunchedEffect(Unit) {
                user.value = getCurrentUser()
            }
            TP4Theme {
                NavigationMainHost(navController = navController, user = user.value)
            }
        }
    }
}