package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth.SampleViewModel

@AndroidEntryPoint
class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<SampleViewModel>()
            Text(viewModel.getSampleText())
        }
    }
}