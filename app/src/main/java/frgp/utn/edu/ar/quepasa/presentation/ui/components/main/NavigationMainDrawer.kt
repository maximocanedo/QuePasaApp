package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Black1
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue2
import kotlinx.coroutines.launch

@Composable
fun NavigationMainDrawer(navController: NavHostController, user: User?) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val user by LocalAuth.current.collectAsState()

    ModalDrawerSheet {
        Column {
            Box(
                modifier = Modifier
                    .background(Blue2)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "${user?.name}",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text(
                            text = "@${user?.username}",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                    IconButton(onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("userProfile")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "User Icon",
                            tint = Color.White
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("home")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home Icon",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Text(
                            text = "Inicio",
                            fontSize = 20.sp,
                            color = Black1,
                            modifier = Modifier.clickable { navController.navigate("home") }
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {

                        } ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_supervised_user_circle_24),
                                contentDescription = "Role Request",
                            )
                        }
                        Text(
                            text = "Roles",
                            fontSize = 20.sp,
                            color = Black1,
                            modifier = Modifier.clickable { navController.navigate("roleRequestUserList") }
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_event_24),
                                contentDescription = "Event Icon",
                            )
                        }
                        Text(
                            text = "Eventos",
                            fontSize = 20.sp,
                            color = Black1,
                            modifier = Modifier.clickable { navController.navigate("events") }
                        )
                    }
                }
            }
        }
    }
}
