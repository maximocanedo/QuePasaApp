package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.dialog.NeighbourhoodDialog
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.TitleField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import java.time.LocalDateTime

@Composable
fun CreateEventScreen(navController: NavHostController, user: User?) {
    val viewModel: EventViewModel = hiltViewModel()
    BaseComponent(navController, user, "Crear de Evento", true) {
        var title: String by remember { mutableStateOf("") }
        var description: String by remember { mutableStateOf("") }
        var address: String by remember { mutableStateOf("") }
        var start: LocalDateTime by remember { mutableStateOf(LocalDateTime.now()) }
        var end: LocalDateTime by remember { mutableStateOf(LocalDateTime.now()) }
        var category: String by remember { mutableStateOf("EDUCATIVE") }
        var audience: String by remember { mutableStateOf("PUBLIC") }
        var neighbourhoods: Set<Long> by remember { mutableStateOf(HashSet()) }

        val openNeighbourhoodDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    validator = viewModel::titleValidator,
                    onChange = { value, valid ->
                        run {
                            title = value
                            viewModel.setTitleIsValid(valid)
                        }
                    }
                )
            }
            Row {
                DescriptionField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    validator = viewModel::descriptionValidator,
                    onChange = { value, valid ->
                        run {
                            description = value
                            viewModel.setDescriptionIsValid(valid)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    openNeighbourhoodDialog.value = true
                }) {
                    Text("Agregar Barrios")
                }
            }
        }



        when {
            openNeighbourhoodDialog.value -> {
                NeighbourhoodDialog(
                    onDismissRequest = { openNeighbourhoodDialog.value = false },
                    neighbourhoods = neighbourhoods,
                    onNeighbourhoodsChange = { neighbourhoods = it }
                )
            }
        }
    }
}