package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.Blue2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBackBar(title: String, navController: NavHostController, backRoute: String = "home") {
    Box {
        TopAppBar(
            title = {
                Text(text = title, color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = {
                   navController.navigate(backRoute)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Blue2
            )
        )
    }
}