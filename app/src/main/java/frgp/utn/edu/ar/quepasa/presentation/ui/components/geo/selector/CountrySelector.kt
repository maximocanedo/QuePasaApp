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
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CountryList
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.GeoChipContainer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.GeographicContextRow
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.StatesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.SubnationalDivisionList



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoSelector(
    modifier: Modifier = Modifier,
    value: Set<Country> = emptySet(),
    countries: List<Country> = emptyList(),
    onCountryLoadRequest: suspend () -> Unit = {  },
    onCountrySelect: (Country) -> Unit = {  },
    onCountryUnselectRequest: (Country) -> Unit = {  },
    onDismiss: () -> Unit = {  },
    isLoading: NeighbourhoodSelectorScreen? = null,
    limit: Int? = null,
    valid: Boolean = true,
    onContinue: () -> Unit = {  }
) {
    val rowModifier: Modifier = Modifier.fillMaxWidth()
    var tab: NeighbourhoodSelectorScreen = NeighbourhoodSelectorScreen.COUNTRY

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(rowModifier) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(if(limit != null && limit == 1) "Seleccioná un país" else "Seleccionar países") },
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
        Row(rowModifier.weight(1f)) {
            AnimatedVisibility(
                visible = true,
                enter = enterTransition,
                exit = exitTransition
            ) {
                CountryList(
                    modifier = Modifier.fillMaxSize(),
                    items = countries,
                    selected = value.toList(),
                    selectable = limit == null || limit > 1,
                    onClick = {
                        onCountrySelect(it)
                    },
                    onCheckedChange = { state, value ->
                        if (value) {
                            onCountrySelect(state)
                        }
                        else onCountryUnselectRequest(state)
                    },
                    isLoading = isLoading != null && isLoading == NeighbourhoodSelectorScreen.STATE,
                    onNextRequest = onCountryLoadRequest
                )
            }
        }
        HorizontalDivider()
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                if (value.isNotEmpty()) {
                    Text(
                        text = if(value.size == 1) "País seleccionado" else "Países seleccionados",
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp))
                }
                GeoChipContainer(
                    data = value,
                    onUnselectRequest = onCountryUnselectRequest
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
fun CountrySelectorPreview() {
    var displayedCountries by remember { mutableStateOf(listOf(ARGENTINA)) }
    var showing by remember { mutableStateOf(true) }
    var selected by remember { mutableStateOf<Set<Country>>(emptySet()) }
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
        value = selected,
        countries = displayedCountries,
        onCountryLoadRequest = {
            displayedCountries = listOf(ARGENTINA)
        },
        onCountrySelect = { selectedCountry: Country ->
            // selected = selected + selectedCountry // Selección múltiple.
            selected = setOf(selectedCountry) // Selección individual

            println("Ciudad seleccionada: ${selectedCountry.label}")
        },
        onCountryUnselectRequest = { unselected: Country ->
            selected = selected - unselected
            println("País deseleccionado: ${unselected.label}")
        },
        onDismiss = { showing = false },
        limit = 1,
        valid = selected.isNotEmpty(),
        onContinue = {  }
    )
}
