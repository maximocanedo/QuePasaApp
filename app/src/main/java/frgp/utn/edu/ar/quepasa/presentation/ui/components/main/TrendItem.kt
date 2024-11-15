package frgp.utn.edu.ar.quepasa.presentation.ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrendItem(tag: String, cantidad: Int) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(60.dp)
            .padding(vertical = 0.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                        )
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tag,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrendItem() {
    MaterialTheme {
        TrendItem(tag = "Trend", cantidad = 2232)
    }
}