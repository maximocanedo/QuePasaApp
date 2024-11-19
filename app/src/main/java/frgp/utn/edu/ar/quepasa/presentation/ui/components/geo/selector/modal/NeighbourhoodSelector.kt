package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.modal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.ARGENTINA
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CitiesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CityList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CountryList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.GeographicContextRow
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.NeighbourhoodChipContainer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.NeighbourhoodList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.NeighbourhoodsMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.StatesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.SubnationalDivisionList
import kotlinx.coroutines.launch

enum class NeighbourhoodSelectorScreen {
    COUNTRY,
    STATE,
    CITY,
    NEIGHBOURHOOD
}

val enterTransition = slideInHorizontally(
    initialOffsetX = { 72 },
    animationSpec = tween(durationMillis = 500)
) + fadeIn(
    initialAlpha = 0f
)

val exitTransition = slideOutHorizontally(
    targetOffsetX = { 72 },
    animationSpec = tween(durationMillis = 500)
) + fadeOut()


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeographicModalSelector(
    modifier: Modifier = Modifier,
    value: Set<Neighbourhood> = emptySet(),
    countries: List<Country> = emptyList(),
    onCountrySelected: (Country) -> Unit = {  },
    onCountryLoadRequest: suspend (Boolean) -> Unit = {  },
    states: List<SubnationalDivision> = emptyList(),
    onStateSelected: (SubnationalDivision) -> Unit = {  },
    onStateLoadRequest: suspend (Boolean) -> Unit = {  },
    cities: List<City> = emptyList(),
    onCitySelected: (City) -> Unit,
    onCityLoadRequest: suspend (Boolean) -> Unit = {  },
    neighbourhoods: List<Neighbourhood> = emptyList(),
    onNeighbourhoodSelect: (Neighbourhood) -> Unit = {  },
    onNeighbourhoodUnselectRequest: (Neighbourhood) -> Unit = {  },
    onNeighbourhoodLoadRequest: suspend (Boolean) -> Unit = {  },
    onDismiss: () -> Unit = {  },
    isLoading: NeighbourhoodSelectorScreen? = null,
    limit: Int? = null,
    valid: Boolean = true,
    onContinue: () -> Unit = {  }
) {
    var city by remember { mutableStateOf<City?>(if(value.isEmpty()) null else value.last().city) }
    var state by remember { mutableStateOf<SubnationalDivision?>(if(value.isEmpty()) null else value.last().city.subdivision) }
    var country by remember { mutableStateOf<Country?>(if(value.isEmpty()) null else value.last().city.subdivision.country) }
    val rowModifier: Modifier = Modifier.fillMaxWidth()
    var tab: NeighbourhoodSelectorScreen by remember { mutableStateOf(if(value.isEmpty()) NeighbourhoodSelectorScreen.COUNTRY else NeighbourhoodSelectorScreen.NEIGHBOURHOOD) }

    LaunchedEffect(1) {
        onCountryLoadRequest(true)
    }
    LaunchedEffect(country) {
        onStateLoadRequest(true)
    }
    LaunchedEffect(state) {
        onCityLoadRequest(true)
    }
    LaunchedEffect(city) {
        onNeighbourhoodLoadRequest(true)
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(rowModifier) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(if(limit != null && limit == 1) "Seleccioná un barrio" else "Seleccionar barrios") },
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
            city = city,
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
                        PullToRefreshBox(
                            state = rememberPullToRefreshState(),
                            isRefreshing = isLoading == NeighbourhoodSelectorScreen.COUNTRY,
                            onRefresh = { scope.launch { onCountryLoadRequest(true) } }) {
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
                                isLoading = isLoading == NeighbourhoodSelectorScreen.COUNTRY,
                                onNextRequest = { onCountryLoadRequest(false) }
                            )
                        }
                    }
                }
                NeighbourhoodSelectorScreen.STATE -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.STATE,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        PullToRefreshBox(
                            state = rememberPullToRefreshState(),
                            isRefreshing = isLoading == NeighbourhoodSelectorScreen.STATE,
                            onRefresh = { scope.launch { onStateLoadRequest(true) } })
                        {
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
                                isLoading = isLoading == NeighbourhoodSelectorScreen.STATE,
                                onNextRequest = { onStateLoadRequest(false) }
                            )
                        }
                    }
                }
                NeighbourhoodSelectorScreen.CITY -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.CITY,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        PullToRefreshBox(
                            state = rememberPullToRefreshState(),
                            isRefreshing = isLoading == NeighbourhoodSelectorScreen.CITY,
                            onRefresh = { scope.launch { onCityLoadRequest(true) } })
                        {
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
                                onNextRequest = { onCityLoadRequest(false) }
                            )
                        }
                    }
                }
                NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> {
                    AnimatedVisibility(
                        visible = tab == NeighbourhoodSelectorScreen.NEIGHBOURHOOD,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        PullToRefreshBox(
                            state = rememberPullToRefreshState(),
                            isRefreshing = isLoading == NeighbourhoodSelectorScreen.NEIGHBOURHOOD,
                            onRefresh = { scope.launch { onNeighbourhoodLoadRequest(true) } })
                        {
                            NeighbourhoodList(
                                modifier = Modifier.fillMaxSize(),
                                items = neighbourhoods,
                                selectable = limit == null || limit > 1,
                                selected = value.toList(),
                                onCheckedChange = { neighbourhood, value ->
                                    if (value) {
                                        onNeighbourhoodSelect(neighbourhood)
                                    } else onNeighbourhoodUnselectRequest(neighbourhood)
                                },
                                showGeographicalContext = false,
                                onClick = {
                                    onNeighbourhoodSelect(it)
                                },
                                isLoading = isLoading == NeighbourhoodSelectorScreen.NEIGHBOURHOOD,
                                onNextRequest = { onNeighbourhoodLoadRequest(false) }
                            )
                        }
                    }
                }
            }
        }
        HorizontalDivider()
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                if (value.isNotEmpty()) {
                    Text(
                        text = if(value.size == 1) "Barrio seleccionado" else "Barrios seleccionados",
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp))
                }
                NeighbourhoodChipContainer(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    data = value,
                    onUnselectRequest = onNeighbourhoodUnselectRequest
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
fun NeighbourhoodSelectorPreview() {
    var displayedCountries by remember { mutableStateOf(listOf(ARGENTINA)) }
    var displayedStates by remember { mutableStateOf(StatesMDFP.take(5)) }
    var displayedCities by remember { mutableStateOf(CitiesMDFP.take(5)) }
    var displayedNeighbourhoods by remember { mutableStateOf(NeighbourhoodsMDFP.take(5)) }
    var showing by remember { mutableStateOf(true) }
    var selectedNeighbourhoods by remember { mutableStateOf(NeighbourhoodsMDFP.take(1).toSet()) }

    if(!showing) Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showing = true }) {
            Text("Mostrar selector")
        }
    }
    if(showing) GeographicModalSelector(
        modifier = Modifier.fillMaxSize(),
        value = selectedNeighbourhoods,
        countries = displayedCountries,
        onCountrySelected = { selectedCountry ->
            println("País seleccionado:  ${selectedCountry.label}")
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
            // selectedNeighbourhoods = selectedNeighbourhoods + selectedNeighbourhood // Selección múltiple.
            selectedNeighbourhoods = setOf(selectedNeighbourhood) // Selección individual

            println("Barrio seleccionado: ${selectedNeighbourhood.name}")
        },
        onNeighbourhoodUnselectRequest = { unselectedNeighbourhood ->
            selectedNeighbourhoods = selectedNeighbourhoods - unselectedNeighbourhood
            println("Barrio deseleccionado: ${unselectedNeighbourhood.name}")
        },
        onNeighbourhoodLoadRequest = {
            displayedNeighbourhoods = NeighbourhoodsMDFP.take(displayedNeighbourhoods.size + 5)
        },
        onDismiss = { showing = false },
        limit = 1,
        valid = selectedNeighbourhoods.isNotEmpty(),
        onContinue = {  }
    )
}
