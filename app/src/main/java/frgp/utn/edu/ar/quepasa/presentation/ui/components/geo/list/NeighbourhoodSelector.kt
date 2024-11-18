package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision

enum class NeighbourhoodSelectorScreen {
    COUNTRY,
    STATE,
    CITY,
    NEIGHBOURHOOD
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeighbourhoodSelector(
    modifier: Modifier = Modifier,
    value: Set<Neighbourhood> = emptySet(),
    countries: List<Country> = emptyList(),
    onCountrySelected: (Country) -> Unit = {  },
    onCountryLoadRequest: suspend () -> Unit = {  },
    states: List<SubnationalDivision> = emptyList(),
    onStateSelected: (SubnationalDivision) -> Unit = {  },
    onStateLoadRequest: suspend () -> Unit = {  },
    cities: List<City> = emptyList(),
    onCitySelected: (City) -> Unit,
    onCityLoadRequest: suspend () -> Unit = {  },
    neighbourhoods: List<Neighbourhood> = emptyList(),
    onNeighbourhoodSelect: (Neighbourhood) -> Unit = {  },
    onNeighbourhoodUnselectRequest: (Neighbourhood) -> Unit = {  },
    onNeighbourhoodLoadRequest: suspend () -> Unit = {  },
    onDismiss: () -> Unit = {  },
    isLoading: NeighbourhoodSelectorScreen? = null,
    limit: Int? = null
) {
    var city by remember { mutableStateOf<City?>(if(value.isEmpty()) null else value.last().city) }
    var state by remember { mutableStateOf<SubnationalDivision?>(if(value.isEmpty()) null else value.last().city.subdivision) }
    var country by remember { mutableStateOf<Country?>(if(value.isEmpty()) null else value.last().city.subdivision.country) }
    val rowModifier: Modifier = Modifier.fillMaxWidth()
    var tab: NeighbourhoodSelectorScreen by remember { mutableStateOf(if(value.isEmpty()) NeighbourhoodSelectorScreen.COUNTRY else NeighbourhoodSelectorScreen.NEIGHBOURHOOD) }
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(rowModifier) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Seleccionar barrio") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
        if(country != null || tab != NeighbourhoodSelectorScreen.COUNTRY) Row(rowModifier.padding(horizontal = 20.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier,
                text = buildAnnotatedString {
                    append("Región: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        when(tab) {
                            NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> {
                                if(city != null) append("${city!!.name}, ${state!!.label}, ${country!!.label}")
                                else if(state != null) append("${state!!.label}, ${country!!.label}")
                                else append(country!!.label)
                            }
                            NeighbourhoodSelectorScreen.CITY -> {
                                if(state != null) append("${state!!.label}, ${country!!.label}")
                                else append(country!!.label)
                            }
                            NeighbourhoodSelectorScreen.STATE -> {
                                append(country!!.label)
                            }
                            NeighbourhoodSelectorScreen.COUNTRY -> {}
                        }
                    }
                }
            )
            if(tab != NeighbourhoodSelectorScreen.COUNTRY) TextButton(onClick = {
                tab = when(tab) {
                    NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> NeighbourhoodSelectorScreen.CITY
                    NeighbourhoodSelectorScreen.CITY -> NeighbourhoodSelectorScreen.STATE
                    NeighbourhoodSelectorScreen.STATE -> NeighbourhoodSelectorScreen.COUNTRY
                    NeighbourhoodSelectorScreen.COUNTRY -> NeighbourhoodSelectorScreen.COUNTRY
                }
            }) {
                Text("Editar")
            }
        }
        Row(rowModifier.weight(1f)) {
            when(tab) {
                NeighbourhoodSelectorScreen.COUNTRY -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.COUNTRY,
                        enter = slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    ) {
                        CountryList(
                            modifier = Modifier.fillMaxSize(),
                            items = countries,
                            onClick = {
                                country = it
                                state = null
                                city = null
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
                        enter = slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    ) {
                        SubnationalDivisionList(
                            modifier = Modifier.fillMaxSize(),
                            items = states,
                            showGeographicalContext = false,
                            onClick = {
                                state = it
                                city = null

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
                        enter = slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    ) {
                        CityList(
                            modifier = Modifier.fillMaxSize(),
                            items = cities,
                            showGeographicalContext = false,
                            onClick = {
                                city = it
                                onCitySelected(it)
                                tab = NeighbourhoodSelectorScreen.NEIGHBOURHOOD
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.CITY,
                            onNextRequest = onCityLoadRequest
                        )
                    }
                }
                NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.NEIGHBOURHOOD,
                        enter = slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    ) {
                        NeighbourhoodList(
                            modifier = Modifier.fillMaxSize(),
                            items = neighbourhoods,
                            selectable = limit == null || limit > 1,
                            selected = value.toList(),
                            onCheckedChange = { neighbourhood, value ->
                                if (value) onNeighbourhoodSelect(neighbourhood)
                                else onNeighbourhoodUnselectRequest(neighbourhood)
                            },
                            showGeographicalContext = false,
                            onClick = {
                                onNeighbourhoodSelect(it)
                            },
                            isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.NEIGHBOURHOOD,
                            onNextRequest = onNeighbourhoodLoadRequest
                        )
                    }
                }
            }
        }
        HorizontalDivider()
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                if (value.isNotEmpty()) {
                    Text(text = "Seleccionado", modifier = Modifier.padding(start = 8.dp, top = 4.dp))
                }
                NeighbourhoodChipContainer(
                    data = value,
                    onUnselectRequest = onNeighbourhoodUnselectRequest
                )
            }
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                Button(onClick = { /*TODO*/ }) {
                    Text("Continuar")
                }
            }
        }
    }
}

@Preview
@Composable
fun NeighbourhoodSelectorPreview() {
    // Estados para controlar la cantidad de elementos cargados
    var displayedCountries by remember { mutableStateOf(listOf(ARGENTINA)) }
    var displayedStates by remember { mutableStateOf(StatesMDFP.take(5)) }
    var displayedCities by remember { mutableStateOf(CitiesMDFP.take(5)) }
    var displayedNeighbourhoods by remember { mutableStateOf(NeighbourhoodsMDFP.take(5)) }

    // Estado para mantener los elementos seleccionados
    var selectedNeighbourhoods by remember { mutableStateOf(NeighbourhoodsMDFP.take(1).toSet()) }

    NeighbourhoodSelector(
        modifier = Modifier.fillMaxSize(),
        value = selectedNeighbourhoods,
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
        onCitySelected = { selectedCity ->
            println("Ciudad seleccionada: ${selectedCity.name}")
        },
        onCityLoadRequest = {
            displayedCities = CitiesMDFP.take(displayedCities.size + 5)
        },
        neighbourhoods = displayedNeighbourhoods,
        onNeighbourhoodSelect = { selectedNeighbourhood ->
            // Agrega el barrio seleccionado al conjunto
            selectedNeighbourhoods = selectedNeighbourhoods + selectedNeighbourhood
            println("Barrio seleccionado: ${selectedNeighbourhood.name}")
        },
        onNeighbourhoodUnselectRequest = { unselectedNeighbourhood ->
            // Quita el barrio del conjunto
            selectedNeighbourhoods = selectedNeighbourhoods - unselectedNeighbourhood
            println("Barrio deseleccionado: ${unselectedNeighbourhood.name}")
        },
        onNeighbourhoodLoadRequest = {
            displayedNeighbourhoods = NeighbourhoodsMDFP.take(displayedNeighbourhoods.size + 5)
        },
        onDismiss = { println("Diálogo cerrado") },
        limit = null
    )
}
