package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.neighbourhood.NeighbourhoodViewModel
import kotlinx.coroutines.launch

@Composable
fun NeighbourhoodDialog(
    onDismissRequest: () -> Unit,
    neighbourhoods: Set<Long>,
    onNeighbourhoodsChange: (Set<Long>) -> Unit
) {
    val searchedNeighbourhood = remember { mutableStateOf("") }
    val viewModel: NeighbourhoodViewModel = hiltViewModel()
    val displayNeighbourhoods by viewModel.neighbourhoods.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.viewModelScope.launch {
            viewModel.getNeighbourhoodsByName(searchedNeighbourhood.value)
        }
    }

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            OutlinedTextField(
                value = searchedNeighbourhood.value,
                onValueChange = {
                    searchedNeighbourhood.value = it
                    viewModel.viewModelScope.launch {
                        viewModel.getNeighbourhoodsByName(searchedNeighbourhood.value)
                    }
                },
                label = { Text("Barrio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                userScrollEnabled = true
            ) {
                items(displayNeighbourhoods.content) { neighbourhood ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = neighbourhood.name,
                            textAlign = TextAlign.Start
                        )
                        IconButton(
                            onClick = {
                                if (neighbourhoods.contains(neighbourhood.id)) {
                                    onNeighbourhoodsChange(neighbourhoods.minus(neighbourhood.id))
                                } else {
                                    onNeighbourhoodsChange(neighbourhoods.plus(neighbourhood.id))
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp),
                        ) {
                            if (neighbourhoods.contains(neighbourhood.id)) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove")
                            } else {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        }
                    }
                }
            }
        }
    }
}