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
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.ARGENTINA
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CitiesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CityChipContainer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CityList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CountryList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.GeographicContextRow
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.StatesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.SubnationalDivisionList

enum class CitySelectorScreen {
    COUNTRY,
    STATE,
    CITY
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySelector(
    modifier: Modifier = Modifier,
    value: Set<City> = emptySet(),
    countries: List<Country> = emptyList(),
    onCountrySelected: (Country) -> Unit = {  },
    onCountryLoadRequest: suspend () -> Unit = {  },
    states: List<SubnationalDivision> = emptyList(),
    onStateSelected: (SubnationalDivision) -> Unit = {  },
    onStateLoadRequest: suspend () -> Unit = {  },
    cities: List<City> = emptyList(),
    onCityLoadRequest: suspend () -> Unit = {  },
    onCitySelect: (City) -> Unit = {  },
    onCityUnselectRequest: (City) -> Unit = {  },
    onDismiss: () -> Unit = {  },
    isLoading: NeighbourhoodSelectorScreen? = null,
    limit: Int? = null,
    valid: Boolean = true,
    onContinue: () -> Unit = {  }
) {
    var state by remember { mutableStateOf<SubnationalDivision?>(if(value.isEmpty()) null else value.last().subdivision) }
    var country by remember { mutableStateOf<Country?>(if(value.isEmpty()) null else value.last().subdivision.country) }
    val rowModifier: Modifier = Modifier.fillMaxWidth()
    var tab: NeighbourhoodSelectorScreen by remember { mutableStateOf(if(value.isEmpty()) NeighbourhoodSelectorScreen.COUNTRY else NeighbourhoodSelectorScreen.CITY) }


    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(rowModifier) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(if(limit != null && limit == 1) "Seleccioná una ciudad" else "Seleccionar ciudades") },
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
            state = state,
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
                                state = null
                                onCountrySelected(it)
                                tab = NeighbourhoodSelectorScreen.STATE
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.COUNTRY,
                            onNextRequest = onCountryLoadRequest
                        )
                    }
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
                            onClick = {
                                state = it
                                onStateSelected(it)
                                tab = NeighbourhoodSelectorScreen.CITY
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.STATE,
                            onNextRequest = onStateLoadRequest
                        )
                    }
                }
                NeighbourhoodSelectorScreen.CITY -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.CITY,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        CityList(
                            modifier = Modifier.fillMaxSize(),
                            items = cities,
                            showGeographicalContext = false,
                            selected = value.toList(),
                            selectable = limit == null || limit > 1,
                            onClick = {
                                onCitySelect(it)
                            },
                            onCheckedChange = { city, value ->
                                if (value) {
                                    onCitySelect(city)
                                }
                                else onCityUnselectRequest(city)
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.CITY,
                            onNextRequest = onCityLoadRequest
                        )
                    }
                }
                NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> {
                    tab = NeighbourhoodSelectorScreen.CITY
                }
            }
        }
        HorizontalDivider()
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                if (value.isNotEmpty()) {
                    Text(
                        text = if(value.size == 1) "Ciudad seleccionada" else "Ciudades seleccionadas",
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp))
                }
                CityChipContainer(
                    data = value,
                    onUnselectRequest = onCityUnselectRequest
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
fun CitySelectorPreview() {
    var displayedCountries by remember { mutableStateOf(listOf(ARGENTINA)) }
    var displayedStates by remember { mutableStateOf(StatesMDFP.take(5)) }
    var displayedCities by remember { mutableStateOf(CitiesMDFP.take(5)) }
    var showing by remember { mutableStateOf(true) }
    var selectedCities by remember { mutableStateOf(CitiesMDFP.take(1).toSet()) }
    if(!showing) Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showing = true }) {
            Text("Mostrar selector")
        }
    }
    if(showing) CitySelector(
        modifier = Modifier.fillMaxSize(),
        value = selectedCities,
        countries = displayedCountries,
        onCountrySelected = { selectedCountry ->
            println("País seleccionado: ${selectedCountry.label}")
        },
        onCountryLoadRequest = {
            displayedCountries = listOf(ARGENTINA)
        },
        states = displayedStates,
        onStateSelected = { selectedState ->
            println("Provincia seleccionada: ${selectedState.label}")
        },
        onStateLoadRequest = {
            displayedStates = StatesMDFP.take(displayedStates.size + 5)
        },
        cities = displayedCities,
        onCityLoadRequest = {
            displayedCities = CitiesMDFP.take(displayedCities.size + 5)
        },
        onCitySelect = { selectedCity ->
            // selectedCities = selectedCities + selectedCity // Selección múltiple.
            selectedCities = setOf(selectedCity) // Selección individual

            println("Ciudad seleccionada: ${selectedCity.name}")
        },
        onCityUnselectRequest = { unselectedNeighbourhood ->
            selectedCities = selectedCities - unselectedNeighbourhood
            println("Barrio deseleccionado: ${unselectedNeighbourhood.name}")
        },
        onDismiss = { showing = false },
        limit = 1,
        valid = selectedCities.isNotEmpty(),
        onContinue = {  }
    )
}
