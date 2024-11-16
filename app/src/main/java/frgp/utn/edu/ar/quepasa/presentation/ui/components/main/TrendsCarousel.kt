package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends.TrendsViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel

@Composable
fun TrendsCarousel(
    trendsViewModel: TrendsViewModel,
    onTagSelected: (String) -> Unit
) {
    val trends = trendsViewModel.trends.observeAsState(initial = emptyList())
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (trends.value.isNotEmpty()) {
            trends.value.forEach { trend ->
                if (trend != null) {
                    TrendItem(
                        tag = trend.tag ?: "Sin etiqueta",
                        cantidad = trend.cantidad ?: 0,
                        onClick = { onTagSelected(trend.tag ?: "Sin etiqueta") }
                    )
                } else {
                    Text(
                        text = "Tendencia no válida",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            Text(
                "No hay tendencias disponibles",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

