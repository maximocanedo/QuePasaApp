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
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.CreateEventScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.EditEventScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.EventDetailedScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.EventsScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostCreateScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostDetailedScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostEditScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.PostScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.RoleUpdateAdminListScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.RoleUpdateUserListScreen
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.RoleUpdateUserRequestScreen
import java.util.UUID

@Composable
fun NavigationMainHost(navController: NavHostController, user: User?) {
    val context = LocalContext.current

    BaseComponent(navController = navController, user = user, title = "¿Qué pasa?", false) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                PostScreen(navController = navController, selectedTag = null)
            }

            composable(
                route = "posts?tag={tag}",
                arguments = listOf(
                    navArgument("tag") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val tag = backStackEntry.arguments?.getString("tag") ?: ""
                PostScreen(navController = navController, selectedTag = tag)
            }

            composable(
                route = "postEdit/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: -1
                if (postId != -1) {
                    PostEditScreen(navController, user, postId)
                }
            }

            composable(
                route = "postDetailedScreen/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId") ?: ""
                if (postId.isNotEmpty()) {
                    PostDetailedScreen(navController, postId.toInt())
                }
            }

            composable("postCreate") {
                PostCreateScreen(navController, user)
            }

        composable("userProfile") {
            ProfileScreen(navController)
        }

        composable("user/{username}") {
            ProfileScreen(navController, it.arguments?.getString("username") ?: "")
        }
            composable("events") {
                EventsScreen(navController)
            }

            composable(
                route = "eventEdit/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
                if (eventId.isNotEmpty()) {
                    EditEventScreen(navController, UUID.fromString(eventId))
                }
            }

            composable(
                route = "eventDetailedScreen/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
                if (eventId.isNotEmpty()) {
                    EventDetailedScreen(navController, UUID.fromString(eventId))
                }
            }

            composable("roleRequestUserList") {
                RoleUpdateUserListScreen(navController)
            }

            composable("roleRequestAdminList") {
                RoleUpdateAdminListScreen(navController)
            }

            composable("roleRequest") {
                RoleUpdateUserRequestScreen(navController)
            }
        }
    }
}
