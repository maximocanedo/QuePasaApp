package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TrendsCarousel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends.TrendsViewModel

@Composable
fun TrendsScreen(navController: NavHostController) {
    val trendsViewModel: TrendsViewModel = hiltViewModel()

    Column(modifier = Modifier.padding(12.dp)) {
        TrendsCarousel(
            trendsViewModel = trendsViewModel,
            onTagSelected = { selectedTag ->
                navController.navigate("posts?tag=$selectedTag")
            }
        )
    }
}
