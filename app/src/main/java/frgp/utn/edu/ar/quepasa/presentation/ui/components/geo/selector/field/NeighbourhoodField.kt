package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.field

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.domain.context.feedback.Feedback
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.ARGENTINA
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.CitiesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.NeighbourhoodsMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.StatesMDFP
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.modal.GeographicModalSelector


@Composable
fun NeighbourhoodField(
    modifier: Modifier = Modifier,
    events: NeighbourhoodFieldLoadEvents = NeighbourhoodFieldDefaults,
    value: Set<Neighbourhood>,
    onContinue: () -> Unit,
    valid: Boolean = true,
    allowMultipleSelection: Boolean = true,
    label: String = if(allowMultipleSelection) "Barrios" else "Barrio",
    feedback: String? = null
) {
    var showing by remember { mutableStateOf(false) }
    GeoMiniField(
        modifier = modifier,
        value = value,
        onEditRequest = { showing = true },
        onUnselect = events.onNeighbourhoodUnselect,
        feedback = if(value.size > 4) "No se pueden seleccionar más de cuatro elementos. " else null
    )
    if(showing)
        GeographicModalSelector(
            modifier = Modifier.fillMaxSize(),
            value = value,
            events.countries,
            events.onCountrySelected,
            events.onCountryLoadRequest,
            events.states,
            events.onStateSelected,
            events.onStateLoadRequest,
            events.cities,
            events.onCitySelected,
            events.onCityLoadRequest,
            events.neighbourhoods,
            events.onNeighbourhoodSelect,
            events.onNeighbourhoodUnselect,
            events.onNeighbourhoodLoadRequest,
            { showing = false },
            events.isLoading,
            if(allowMultipleSelection) null else 1,
            valid,
            {
                onContinue()
                showing = false
            }
        )

}

@Composable @Preview
fun NeighbourhoodFieldPreview() {
    var displayedCountries by remember { mutableStateOf(listOf(ARGENTINA)) }
    var displayedStates by remember { mutableStateOf(StatesMDFP.take(5)) }
    var displayedCities by remember { mutableStateOf(CitiesMDFP.take(5)) }
    var displayedNeighbourhoods by remember { mutableStateOf(NeighbourhoodsMDFP.take(5)) }
    var showing by remember { mutableStateOf(true) }
    var selectedNeighbourhoods by remember { mutableStateOf(NeighbourhoodsMDFP.take(1).toSet()) }

    NeighbourhoodField(
        modifier = Modifier,
        events = NeighbourhoodFieldDefaults.copy(
            countries = displayedCountries,
            states = displayedStates,
            cities = displayedCities,
            neighbourhoods = displayedNeighbourhoods,
            onCountryLoadRequest = {
                displayedCountries = listOf(ARGENTINA)
            },
            onStateLoadRequest = {
                displayedStates = StatesMDFP.take(displayedStates.size + 5)
            },
            onCityLoadRequest = {
                displayedCities = CitiesMDFP.take(displayedCities.size + 5)
            },
            onNeighbourhoodSelect = { selectedNeighbourhood ->
                // selectedNeighbourhoods = selectedNeighbourhoods + selectedNeighbourhood // Selección múltiple.
                selectedNeighbourhoods = setOf(selectedNeighbourhood) // Selección individual
            },
            onNeighbourhoodUnselect = { unselectedNeighbourhood ->
                selectedNeighbourhoods = selectedNeighbourhoods - unselectedNeighbourhood
            },
            onNeighbourhoodLoadRequest = {
                displayedNeighbourhoods = NeighbourhoodsMDFP.take(displayedNeighbourhoods.size + 5)
            },
        ),
        value = selectedNeighbourhoods,
        onContinue = {  }
    )
}