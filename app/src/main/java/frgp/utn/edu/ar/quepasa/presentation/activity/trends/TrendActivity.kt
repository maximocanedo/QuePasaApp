package frgp.utn.edu.ar.quepasa.presentation.activity.trends

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.main.TrendsCarousel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends.TrendsViewModel

@AndroidEntryPoint
class TrendActivity : AuthenticatedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val trendsViewModel: TrendsViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                trendsViewModel.loadTrends(
                    barrio = 1,
                    fechaBase = "2024-10-01"
                )
            }

            TrendActivityContent(trendsViewModel)
        }
    }
}

@Composable
fun TrendActivityContent(trendsViewModel: TrendsViewModel) {
    Column {
        Text("Estás autenticado desde una actividad diferente.")
        TrendsCarousel(trendsViewModel = trendsViewModel)
    }
}
