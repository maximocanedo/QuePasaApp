package frgp.utn.edu.ar.quepasa.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalSnack
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.NavigationMainDrawer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TopBackBar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TopMainBar

@Composable
fun BaseComponent(
    navController: NavHostController,
    user: User?,
    title: String,
    back: Boolean,
    backRoute: String = "home",
    content: @Composable () -> Unit
) {
    val snack = LocalSnack.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = { NavigationMainDrawer(navController, user) }) {
        Scaffold(
            topBar = { if(back) TopBackBar(title, navController, backRoute) else TopMainBar(title, scope, drawerState) },
            snackbarHost = { SnackbarHost(hostState = snack) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    }
}