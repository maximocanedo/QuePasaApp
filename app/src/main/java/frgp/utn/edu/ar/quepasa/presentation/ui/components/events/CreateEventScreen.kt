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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.dto.request.EventCreateRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
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
import kotlinx.coroutines.launch
import quepasa.api.exceptions.ValidationError
import java.time.format.DateTimeFormatter

@Composable
fun CreateEventScreen(navController: NavHostController, user: User?) {
    val viewModel: EventViewModel = hiltViewModel()
    val imageViewModel = ImageViewModel()
    BaseComponent(navController, user, "Crear de Evento", true) {
        val title by viewModel.title.collectAsState()
        val description by viewModel.description.collectAsState()
        val address by viewModel.address.collectAsState()
        val start by viewModel.start.collectAsState()
        val end by viewModel.end.collectAsState()
        val category by viewModel.category.collectAsState()
        val audience by viewModel.audience.collectAsState()
        val neighbourhoods by viewModel.neighbourhoods.collectAsState()
        val neighbourhoodsNames by viewModel.neighbourhoodsNames.collectAsState()

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
                            viewModel.setTitle(value)
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
                            viewModel.setDescription(value)
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
                            viewModel.setAddress(value)
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
                        validator = {
                            viewModel.startValidator(it)
                            viewModel.endValidator(end)
                        },
                        onChange = { value, valid ->
                            run {
                                viewModel.setStart(value)
                                viewModel.setStartDateIsValid(valid)
                                try {
                                    viewModel.endValidator(end).build()
                                    viewModel.setEndDateIsValid(true)
                                } catch (e: ValidationError) {
                                    viewModel.setEndDateIsValid(false)
                                    viewModel.setEndDateErrorMessage(e.errors.first() ?: "")
                                }
                            }
                        },
                        label = "Fecha de inicio",
                        isError = !viewModel.startDateIsValid.collectAsState().value,
                        errorMessage = viewModel.startDateErrorMessage,
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    DateField(
                        modifier = Modifier.widthIn(120.dp, 240.dp),
                        value = end,
                        validator = {
                            viewModel.endValidator(it)
                        },
                        onChange = { value, valid ->
                            run {
                                viewModel.setEnd(value)
                                viewModel.setEndDateIsValid(valid)
                                try {
                                    viewModel.startValidator(start).build()
                                    viewModel.setStartDateIsValid(true)
                                } catch (e: ValidationError) {
                                    viewModel.setStartDateIsValid(false)
                                    viewModel.setStartDateErrorMessage(e.errors.first() ?: "")
                                }
                            }
                        },
                        label = "Fecha de fin",
                        isError = !viewModel.endDateIsValid.collectAsState().value,
                        errorMessage = viewModel.endDateErrorMessage,
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
                        onItemSelected = {
                            viewModel.setAudience(it)
                        }
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    EventCategoryField(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        category = category,
                        onItemSelected = {
                            viewModel.setCategory(it)
                        }
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
                        modifier = Modifier.fillMaxWidth(),
                        isError = !viewModel.neighbourhoodIsValid.collectAsState().value,
                        supportingText = {
                            if (!viewModel.neighbourhoodIsValid.collectAsState().value) {
                                Text("Debe seleccionar al menos un barrio")
                            }
                        },

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
                        viewModel.viewModelScope.launch {
                            if (viewModel.isEventValid()) {
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                                val startDate = start.format(formatter)
                                val endDate = end.format(formatter)
                                val request = EventCreateRequest(
                                    title = title,
                                    description = description,
                                    address = address,
                                    startDate = startDate,
                                    endDate = endDate,
                                    category = EventCategory.valueOf(category),
                                    audience = Audience.valueOf(audience),
                                    neighbourhoods = neighbourhoods
                                )
                                viewModel.createEvent(request)
                            }
                        }
                    },
                    enabled = viewModel.isEventValid(),
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
                        viewModel.setNeighbourhoods(newNeighbourhoods)
                        viewModel.setNeighbourhoodsNames(newNeighbourhoodsNames)
                        viewModel.setNeighbourhoodIsValid(
                            viewModel.neighbourhoodValidator(
                                newNeighbourhoods
                            )
                        )
                    }
                )
            }
        }
    }
}