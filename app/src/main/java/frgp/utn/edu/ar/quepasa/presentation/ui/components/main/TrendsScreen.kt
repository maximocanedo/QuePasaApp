package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends.TrendsViewModel

import androidx.compose.runtime.LaunchedEffect
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationContext

@Composable
fun TrendsScreen(navController: NavHostController, user: AuthenticationContext) {
    val trendsViewModel: TrendsViewModel = hiltViewModel()

    LaunchedEffect(user) {
        trendsViewModel.setUser(user)
    }

    Column(modifier = Modifier.padding(12.dp)) {
        TrendsCarousel(
            trendsViewModel = trendsViewModel,
            onTagSelected = { selectedTag ->
                navController.navigate("posts?tag=$selectedTag")
            }
        )
    }
}
