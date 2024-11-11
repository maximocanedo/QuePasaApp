package frgp.utn.edu.ar.quepasa.presentation.ui.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.dialog.NeighbourhoodDialog
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.AddressField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.DateField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.EventAudienceField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.EventCategoryField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields.TitleField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.ImageField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.previews.ImagesPreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.events.EventViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import quepasa.api.validators.events.EventDateValidator
import java.time.LocalDateTime

@Composable
fun CreateEventScreen(navController: NavHostController, user: User?) {
    val viewModel: EventViewModel = hiltViewModel()
    val imageViewModel = ImageViewModel()
    BaseComponent(navController, user, "Crear de Evento", true) {
        var title: String by remember { mutableStateOf("") }
        var description: String by remember { mutableStateOf("") }
        var address: String by remember { mutableStateOf("") }
        var start: LocalDateTime by remember { mutableStateOf(LocalDateTime.now()) }
        var end: LocalDateTime by remember { mutableStateOf(LocalDateTime.now()) }
        var category: String by remember { mutableStateOf("EDUCATIVE") }
        var audience: String by remember { mutableStateOf("PUBLIC") }
        var neighbourhoods: Set<Long> by remember { mutableStateOf(HashSet()) }
        var neighbourhoodsNames: List<String> by remember { mutableStateOf(ArrayList()) }

        val openNeighbourhoodDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
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
            Row {
                AddressField(
                    modifier = Modifier.fillMaxWidth(),
                    value = address,
                    validator = viewModel::addressValidator,
                    onChange = { value, valid ->
                        run {
                            address = value
                            viewModel.setAddressIsValid(valid)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    DateField(
                        modifier = Modifier.fillMaxWidth(),
                        value = start,
                        validator = viewModel::startDateValidator,
                        onChange = { value, valid ->
                            run {
                                start = value
                                viewModel.setStartDateIsValid(valid)
                            }
                        },
                        label = "Fecha de inicio"
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    DateField(
                        modifier = Modifier.widthIn(120.dp, 240.dp),
                        value = end,
                        validator = {
                            EventDateValidator(it)
                                .isNotNull().isNotPast().isAfterStartDate(start)
                        },
                        onChange = { value, valid ->
                            run {
                                end = value
                                viewModel.setEndDateIsValid(valid)
                            }
                        },
                        label = "Fecha de fin"
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    EventAudienceField(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        audience = audience,
                        onItemSelected = { audience = it }
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    EventCategoryField(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        category = category,
                        onItemSelected = { category = it }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.70f)
                ) {
                    OutlinedTextField(
                        value = neighbourhoodsNames.joinToString(", "),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Barrios") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            openNeighbourhoodDialog.value = true
                        },
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Agregar Barrios")
                    }
                }
            }
            Row {
                ImagesPreview(modifier = Modifier, viewModel = imageViewModel)
                ImageField(modifier = Modifier.fillMaxWidth(), viewModel = imageViewModel)
            }
            Row {
                Button(
                    onClick = {
                        CoroutineScope(IO).launch {
                            /*
                            val validation = viewModel.
                            println("Validation $validation")
                            if (validation) {
                                val result = viewModel.createEvent(
                                    title = title,
                                    description = description,
                                    address = address,
                                    start = start,
                                    end = end,
                                    category = category,
                                    audience = audience,
                                    neighbourhoods = neighbourhoods
                                )
                            }
                             */
                        }
                    }
                ) {
                    Text("Crear Evento")
                }
            }
        }

        when {
            openNeighbourhoodDialog.value -> {
                NeighbourhoodDialog(
                    onDismissRequest = { openNeighbourhoodDialog.value = false },
                    neighbourhoods = neighbourhoods,
                    neighbourhoodsNames = neighbourhoodsNames,
                    onNeighbourhoodsChange = { newNeighbourhoods, newNeighbourhoodsNames ->
                        neighbourhoods = newNeighbourhoods
                        neighbourhoodsNames = newNeighbourhoodsNames
                    }
                )
            }
        }
    }
}