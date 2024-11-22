package frgp.utn.edu.ar.quepasa.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadMoreButton(
    modifier: Modifier = Modifier,
    loadable: Boolean = true,
    loading: Boolean = false,
    label: String = "Mostrar más",
    loadingLabel: String = "Cargando",
    onLoadRequest: suspend () -> Unit = {  }
) {
    Column(
        modifier = Modifier.padding(if(loading) 12.dp else 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        if(loading) CircularProgressIndicator(
            modifier = Modifier
        )
        if(loading) Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            text = loadingLabel,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        if(loadable && !loading) TextButton(
            modifier = modifier,
            onClick = { CoroutineScope(IO).launch { onLoadRequest() } }
        ) { Text(label) }
    }

}

@Composable
@Preview fun LoadMoreButtonPreview() {
    var loadable by remember { mutableStateOf(true) }
    var loading by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()) {
        ListItem(
            trailingContent = { Switch(checked = loadable, onCheckedChange = {loadable = it}) },
            headlineContent = { Text("Habilitar carga") }
        )
        ListItem(
            trailingContent = { Switch(checked = loading, onCheckedChange = {loading = it}) },
            headlineContent = { Text("Estado cargando") }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LoadMoreButton(
                loadable = loadable,
                loading = loading,
                onLoadRequest = {
                    loading = true
                    delay(3000)
                    loading = false
                }
            )
        }
    }

}