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

@Composable
fun TrendsCarousel(
    trendsViewModel: TrendsViewModel
) {
    val trends = trendsViewModel.trends.observeAsState(initial = emptyList())

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),  // Menor espacio entre elementos
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (trends.value.isNotEmpty()) {
            trends.value.forEach { trend ->
                TrendItem(tag = trend.tag ?: "Sin etiqueta", cantidad = trend.cantidad)
            }
        } else {
            Text(
                "No hay tendencias disponibles",
                fontSize = 12.sp,  // Tamaño de fuente más pequeño
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
