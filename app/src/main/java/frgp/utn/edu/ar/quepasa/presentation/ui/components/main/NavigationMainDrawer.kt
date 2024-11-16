package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.source.remote.saveAuthToken
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.LoginActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserHorizontalButton
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Black1
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue2
import frgp.utn.edu.ar.quepasa.utils.role.roleLowerThan
import kotlinx.coroutines.launch

val ACTION_HOME: String = "home"
val ROLE_REQUEST_USER_LIST: String = "roleRequestUserList"
val ROLE_REQUEST_ADMIN_LIST: String = "roleRequestAdminList"
val ACTION_EVENTS: String = "events"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationMainDrawer(navController: NavHostController, user: User?) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }
    val user by LocalAuth.current.collectAsState()
    val curr = navController.currentBackStackEntry?.destination?.route ?: ""
    ModalDrawerSheet {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 12.dp, horizontal = 8.dp),
            verticalArrangement = spacedBy(4.dp)
        ) {
            if (user.user != null) UserHorizontalButton(
                user = user.user!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate("userProfile")
                }
            )
            NavigationDrawerItem(label = {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = "Inicio"
                )
            },
                selected = curr.equals(ACTION_HOME),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home"
                    )
                },
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(ACTION_HOME)
                }
            )

            NavigationDrawerItem(label = {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = "Solicitudes de cambio de rol"
                )
            },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.file_copy),
                        contentDescription = "Home"
                    )
                },
                selected = curr.equals(ROLE_REQUEST_USER_LIST) || curr.equals(
                    ROLE_REQUEST_ADMIN_LIST
                ),
                onClick = {
                    scope.launch { drawerState.close() }
                    val currentRole: Role? = user.user?.role
                    if (currentRole != null) {
                        if (roleLowerThan(currentRole, Role.MOD)) {
                            navController.navigate(ROLE_REQUEST_USER_LIST)
                        } else {
                            navController.navigate(ROLE_REQUEST_ADMIN_LIST)
                        }
                    }
                }
            )
            NavigationDrawerItem(label = {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = "Eventos"
                )
            },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.event),
                        contentDescription = "Home"
                    )
                },
                selected = curr.equals(ACTION_EVENTS),
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(ACTION_EVENTS)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            NavigationDrawerItem(
                label = {
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = "Cerrar sesión"
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Home"
                    )
                },
                selected = false,
                onClick = {
                    scope.launch { drawerState.close() }
                    showLogoutDialog = true
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedTextColor = MaterialTheme.colorScheme.onErrorContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )

        }
    }
    if(showLogoutDialog) AlertDialog(
        title = { Text("Estás a punto de cerrar sesión") },
        text = { Text("¿Seguro de continuar?") },
        onDismissRequest = {
            showLogoutDialog = false
        },
        confirmButton = {
            TextButton(onClick = {
                saveAuthToken(context, "")
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Cerrar sesión")
            }
        },
        dismissButton = {
            TextButton(onClick = { showLogoutDialog = false }) {
                Text("Volver")
            }
        }
    )
}
