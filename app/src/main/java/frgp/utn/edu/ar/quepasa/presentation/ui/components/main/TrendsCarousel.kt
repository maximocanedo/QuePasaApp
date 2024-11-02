package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends.TrendsViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TrendsCarousel(
    trendsViewModel: TrendsViewModel = hiltViewModel()
) {
    val trends = trendsViewModel.trends.observeAsState(initial = emptyList())

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (trends.value.isNotEmpty()) {
            trends.value.forEach { trend ->
                Badge(
                    modifier = Modifier.padding(4.dp),
                    content = { trend.tag?.let { Text(text = it) } }
                )
            }
        } else {
            Text("No hay tendencias disponibles")
        }
    }
}









