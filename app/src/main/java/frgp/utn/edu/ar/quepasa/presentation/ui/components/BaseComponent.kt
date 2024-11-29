import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalSnack
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.NavigationMainDrawer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TopBackBar
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TopMainBar

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun BaseComponent(
    navController: NavHostController,
    title: String,
    back: Boolean,
    backRoute: String = "home",
    content: @Composable () -> Unit
) {
    val snack = LocalSnack.current
    val user by LocalAuth.current.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val gestureStartEdge = 50.dp.toPx()
                    when {
                        drawerState.currentValue == DrawerValue.Closed &&
                                change.position.x <= gestureStartEdge && dragAmount > 0 -> {
                            scope.launch { drawerState.open() }
                        }
                        drawerState.currentValue == DrawerValue.Open &&
                                dragAmount < 0 -> {
                            scope.launch { drawerState.close() }
                        }
                    }
                }
            }
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = false,
            drawerContent = { NavigationMainDrawer(navController, user.user) }
        ) {
            Scaffold(
                topBar = {
                    if (back) TopBackBar(title, navController, backRoute)
                    else TopMainBar(title, scope, drawerState)
                },
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
}
