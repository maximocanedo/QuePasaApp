package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.activity.users.ProfileScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.CreateEventScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostCreateScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostEditScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.UserProfileScreen

@Composable
fun NavigationMainHost(navController: NavHostController, user: User?) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { MainPage(navController) }
        composable("userProfile") {
            val intent = Intent(context, ProfileScreen::class.java)
            // intent.putExtra("username", "") // No agregamos nada para que nos muestre el perfil del usuario autenticado.
            context.startActivity(intent)
        }
        composable("postCreate") { PostCreateScreen(navController, user) }
        composable("postEdit") { PostEditScreen(navController, user) }
        composable("eventCreate") { CreateEventScreen(navController, user) }
        composable("trends") { TrendsScreen() }
    }
}
