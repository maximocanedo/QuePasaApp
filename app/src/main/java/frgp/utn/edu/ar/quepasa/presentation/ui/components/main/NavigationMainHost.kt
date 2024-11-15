package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.activity.users.ProfileScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.CreateEventScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.EditEventScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.EventDetailedScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.EventsScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostCreateScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostEditScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.RoleUpdateAdminListScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.RoleUpdateUserListScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.RoleUpdateUserRequestScreen
import java.util.UUID

@Composable
fun NavigationMainHost(navController: NavHostController, user: User?) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { MainPage(navController) }
        composable("trends") { TrendsScreen() }
        composable("userProfile") {
            val intent = Intent(context, ProfileScreen::class.java)
            // intent.putExtra("username", "") // No agregamos nada para que nos muestre el perfil del usuario autenticado.
            context.startActivity(intent)
        }
        composable("postCreate") { PostCreateScreen(navController, user) }
        composable(
            route = "postEdit/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: -1
            if (postId != -1) {
                PostEditScreen(navController, user, postId)
            }
        }
        composable("eventCreate") { CreateEventScreen(navController) }
        composable(
            route = "eventEdit/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            if (eventId.isNotEmpty()) {
                EditEventScreen(navController, UUID.fromString(eventId))
            }
        }
        composable("events") { EventsScreen(navController) }
        composable(
            route = "eventDetailedScreen/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            if (eventId.isNotEmpty()) {
                EventDetailedScreen(navController, UUID.fromString(eventId))
            }
        }
        composable("roleRequestUserList") { RoleUpdateUserListScreen(navController)}
        composable("roleRequestAdminList") { RoleUpdateAdminListScreen(navController) }
        composable("roleRequest") { RoleUpdateUserRequestScreen(navController) }
    }
}
