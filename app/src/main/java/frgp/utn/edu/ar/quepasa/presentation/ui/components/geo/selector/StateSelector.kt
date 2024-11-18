package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.ARGENTINA
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CitiesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CountryList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.GeoChipContainer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.GeographicContextRow
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.StatesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.SubnationalDivisionList



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoSelector(
    modifier: Modifier = Modifier,
    value: Set<SubnationalDivision> = emptySet(),
    countries: List<Country> = emptyList(),
    onCountrySelected: (Country) -> Unit = {  },
    onCountryLoadRequest: suspend () -> Unit = {  },
    states: List<SubnationalDivision> = emptyList(),
    onStateLoadRequest: suspend () -> Unit = {  },
    onStateSelect: (SubnationalDivision) -> Unit = {  },
    onStateUnselectRequest: (SubnationalDivision) -> Unit = {  },
    onDismiss: () -> Unit = {  },
    isLoading: NeighbourhoodSelectorScreen? = null,
    limit: Int? = null,
    valid: Boolean = true,
    onContinue: () -> Unit = {  }
) {
    var country by remember { mutableStateOf<Country?>(if(value.isEmpty()) null else value.last().country) }
    val rowModifier: Modifier = Modifier.fillMaxWidth()
    var tab: NeighbourhoodSelectorScreen by remember { mutableStateOf(if(value.isEmpty()) NeighbourhoodSelectorScreen.COUNTRY else NeighbourhoodSelectorScreen.STATE) }

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(rowModifier) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(if(limit != null && limit == 1) "Seleccioná una región" else "Seleccionar regiones") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                actions = {
                    IconButton(
                        onClick = onDismiss
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Cerrar"
                        )
                    }
                }
            )
        }
        GeographicContextRow(
            city = null,
            state = null,
            country = country,
            tab = tab,
            onTabUpdateRequest = { tab = it }
        )
        Row(rowModifier.weight(1f)) {
            when(tab) {
                NeighbourhoodSelectorScreen.COUNTRY -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.COUNTRY,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        CountryList(
                            modifier = Modifier.fillMaxSize(),
                            items = countries,
                            onClick = {
                                country = it
                                onCountrySelected(it)
                                tab = NeighbourhoodSelectorScreen.STATE
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.COUNTRY,
                            onNextRequest = onCountryLoadRequest
                        )
                    }
                }
                NeighbourhoodSelectorScreen.CITY -> {
                    tab = NeighbourhoodSelectorScreen.STATE
                }
                NeighbourhoodSelectorScreen.STATE -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.STATE,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        SubnationalDivisionList(
                            modifier = Modifier.fillMaxSize(),
                            items = states,
                            showGeographicalContext = false,
                            selected = value.toList(),
                            selectable = limit == null || limit > 1,
                            onClick = {
                                onStateSelect(it)
                            },
                            onCheckedChange = { state, value ->
                                if (value) {
                                    onStateSelect(state)
                                }
                                else onStateUnselectRequest(state)
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.STATE,
                            onNextRequest = onStateLoadRequest
                        )
                    }
                }
                NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> {
                    tab = NeighbourhoodSelectorScreen.STATE
                }
            }
        }
        HorizontalDivider()
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                if (value.isNotEmpty()) {
                    Text(
                        text = if(value.size == 1) "Región seleccionada" else "Regiones seleccionadas",
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp))
                }
                GeoChipContainer(
                    data = value,
                    onUnselectRequest = onStateUnselectRequest
                )
            }
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                Button(onClick = onContinue, enabled = valid) {
                    Text("Continuar")
                }
            }
        }
    }
}

@Preview
@Composable
fun StateSelectorPreview() {
    var displayedCountries by remember { mutableStateOf(listOf(ARGENTINA)) }
    var displayedStates by remember { mutableStateOf(StatesMDFP.take(5)) }
    var showing by remember { mutableStateOf(true) }
    var selectedStates by remember { mutableStateOf(StatesMDFP.take(1).toSet()) }
    if(!showing) Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showing = true }) {
            Text("Mostrar selector")
        }
    }
    if(showing) GeoSelector(
        modifier = Modifier.fillMaxSize(),
        value = selectedStates,
        countries = displayedCountries,
        onCountrySelected = { selectedCountry ->
            println("País seleccionado: ${selectedCountry.label}")
        },
        onCountryLoadRequest = {
            displayedCountries = listOf(ARGENTINA)
        },
        states = displayedStates,
        onStateLoadRequest = {
            displayedStates = StatesMDFP.take(displayedStates.size + 5)
        },
        onStateSelect = { selected ->
            // selectedStates = selectedStates + selected // Selección múltiple.
            selectedStates = setOf(selected) // Selección individual

            println("Ciudad seleccionada: ${selected.label}")
        },
        onStateUnselectRequest = { unselectedNeighbourhood ->
            selectedStates = selectedStates - unselectedNeighbourhood
            println("Región deseleccionada: ${unselectedNeighbourhood.label}")
        },
        onDismiss = { showing = false },
        limit = 1,
        valid = selectedStates.isNotEmpty(),
        onContinue = {  }
    )
}
