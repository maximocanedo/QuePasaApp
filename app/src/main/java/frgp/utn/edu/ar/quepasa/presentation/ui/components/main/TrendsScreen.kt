package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends.TrendsViewModel

@Composable
fun TrendsScreen() {
    val trendsViewModel: TrendsViewModel = hiltViewModel()

    Column(modifier = Modifier.padding(12.dp)) {
        
        TrendsCarousel(trendsViewModel = trendsViewModel)
    }
}

