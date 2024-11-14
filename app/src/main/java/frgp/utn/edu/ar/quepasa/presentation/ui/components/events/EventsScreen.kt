package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.EventCard
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel

@Composable
fun EventsScreen() {
    val viewModel: EventViewModel = hiltViewModel()
    val events by viewModel.events.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getEvents()
    }
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = true
    ) {
        items(events.content) { event ->
            EventCard(event = event)
        }
    }
}