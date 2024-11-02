package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue1
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue2

@Composable
fun NavigationMainDrawer(navController: NavHostController, user: User?, topBarText: String) { // TODO: Change to User (non-nullable) after login is implemented
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
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
                                text = "Nombre Apellido ${user?.name}",
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Usuario ${user?.username}",
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                        IconButton(onClick = { navController.navigate("userProfile") }) {
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
                        .background(Blue1)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        Button(onClick = { navController.navigate("home")} ) {
                            Text(text = "Inicio")
                        }
                    }
                }
            }
        },
        scrimColor = Color.White
    )
    {
        TopMainBar(title = topBarText, scope, drawerState)
    }
}