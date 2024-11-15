package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.card.EventCard
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(navController: NavHostController, user: User?) {
    val viewModel: EventViewModel = hiltViewModel()
    val events by viewModel.events.collectAsState()

    val category by remember { mutableStateOf(EventCategory.EDUCATIVE) }
    var search by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getEvents()
    }
    BaseComponent(navController, user, "Listado Eventos", false) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = search,
                    onValueChange = {
                        search = it
                        viewModel.viewModelScope.launch {
                            viewModel.getEvents(search)
                        }
                    },
                    label = {
                        Text("Buscar")
                    },
                    placeholder = {
                        Text("Curso de ...")
                    }
                )
            }
            Row {
                /* TODO busqueda por categoria */
            }
            Row {
                LazyColumn(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = true
                ) {
                    items(events.content) { event ->
                        EventCard(navController, event)
                    }
                }
            }
        }
    }
}