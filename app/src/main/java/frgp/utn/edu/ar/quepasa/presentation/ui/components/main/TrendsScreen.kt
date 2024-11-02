package frgp.utn.edu.ar.quepasa.presentation.ui.components.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrendsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tendencias", modifier = Modifier.padding(bottom = 8.dp))
        TrendsCarousel()
    }
}



