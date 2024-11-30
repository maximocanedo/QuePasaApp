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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseComponent(
    navController: NavHostController,
    title: String,
    back: Boolean,
    currentRoute: String,
    content: @Composable () -> Unit
) {
    val snack = LocalSnack.current
    val user by LocalAuth.current.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navigationRoutes = mapOf(
        "home" to "events",
        "posts" to "home",
        "userProfile" to "home",
        "advancedProfileSettings" to "userProfile",
        "totpSettings" to "advancedProfileSettings",
        "updatePassword" to "advancedProfileSettings",
        "postList" to "posts",
        "postCreate" to "posts",
        "postEdit" to "posts",
        "postDetailedScreen" to "posts",
        "events" to "userProfile",
        "eventCreate" to "events",
        "eventEdit" to "events",
        "eventDetailedScreen" to "events",
        "roleRequestUserList" to "home",
        "roleRequestAdminList" to "roleRequestUserList",
        "roleRequest" to "roleRequestAdminList"
    )

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
                        drawerState.currentValue == DrawerValue.Closed && dragAmount < -50 -> {
                            val previousRoute = navigationRoutes[currentRoute]
                            previousRoute?.let {
                                navController.navigate(it) {
                                    popUpTo(it) { inclusive = true }
                                }
                            }
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
                    if (back) TopBackBar(title, navController, navigationRoutes[currentRoute] ?: "home")
                    else TopMainBar(title, scope, drawerState)
                },
                snackbarHost = { SnackbarHost(hostState = snack) },
                bottomBar = {
                    BottomAppBar {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { navController.navigate("home") }) {
                                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Blue)
                            }
                            IconButton(onClick = { navController.navigate("events") }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.event),
                                    contentDescription = "Events",
                                    tint = Color.Blue
                                )
                            }
                            IconButton(onClick = { navController.navigate("userProfile") }) {
                                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Blue)
                            }
                        }
                    }
                }
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
