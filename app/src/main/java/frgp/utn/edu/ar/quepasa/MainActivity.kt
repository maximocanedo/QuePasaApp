package frgp.utn.edu.ar.quepasa

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.TopMainBar
import frgp.utn.edu.ar.quepasa.presentation.ui.theme.TP4Theme

class MainActivity : AuthenticatedActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP4Theme {
                Scaffold(
                    topBar = { TopMainBar(title = "¿Qué pasa?") }
                ) {

                }
            }
        }
    }
}