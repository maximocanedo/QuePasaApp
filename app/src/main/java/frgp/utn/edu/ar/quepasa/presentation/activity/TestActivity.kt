package frgp.utn.edu.ar.quepasa.presentation.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.field.NeighbourhoodField

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var n by remember { mutableStateOf(emptySet<Neighbourhood>()) }
            NeighbourhoodField(
                value = n,
                onSelect = { n = n.plus(it) },
                onUnselect = { n = n.minus(it) },
                onContinue = {  },
                useViewModel = true
            )
        }
    }


}