package frgp.utn.edu.ar.quepasa.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue2

@Composable
fun NavigationMainDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .background(Blue2)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Usuario", fontSize = 20.sp, color = Color.White)
            }
        },
        scrimColor = Color.White
    )
    {
        TopMainBar(title = "¿Qué pasa?", scope, drawerState)
    }
}