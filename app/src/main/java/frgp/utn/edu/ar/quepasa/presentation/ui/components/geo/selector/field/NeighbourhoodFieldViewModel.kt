package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.field

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.handle
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.domain.repository.geo.GeoRepository
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.modal.NeighbourhoodSelectorScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class NeighbourhoodFieldViewModel @Inject constructor(
    private val geoRepository: GeoRepository
): ViewModel() {

    var country = MutableStateFlow<Country?>(null)
    var state = MutableStateFlow<SubnationalDivision?>(null)
    var city = MutableStateFlow<City?>(null)

    var loadingState = MutableStateFlow<NeighbourhoodSelectorScreen?>(null)

    var nPage = MutableStateFlow(0)
    var neighbourhoods = mutableStateListOf<Neighbourhood>()
        private set
    suspend fun updateNeighbourhoods(reset: Boolean = false) {
        if(city.value == null) return
        loadingState.update { NeighbourhoodSelectorScreen.NEIGHBOURHOOD }
        nPage.updateAndGet { it + 1 }
        if(reset || neighbourhoods.isEmpty()) {
            nPage.update { 0 }
            neighbourhoods.clear()
        }
        geoRepository
            .getNeighbourhoods(q = "", cityId = city.value!!.id, page = nPage.value)
            .handle {
                if(it == null) return@handle
                neighbourhoods.addAll(it.content)
                loadingState.update { null }
            }
    }

    var cPage = MutableStateFlow(0)
    var cities = mutableStateListOf<City>()
        private set
    suspend fun updateCities(reset: Boolean = false) {
        if(state.value == null) return
        loadingState.update { NeighbourhoodSelectorScreen.CITY }
        cPage.updateAndGet { it + 1 }
        if(reset || cities.isEmpty()) {
            cPage.update { 0 }
            cities.clear()
        }
        geoRepository
            .getCities(q = "", stateIso3 = state.value!!.iso3, page = cPage.value)
            .handle {
                if(it == null) return@handle
                cities.addAll(it.content)
                neighbourhoods.clear()
                loadingState.update { null }
            }
    }

    var sPage = MutableStateFlow(0)
    var states = mutableStateListOf<SubnationalDivision>()
        private set
    suspend fun updateStates(reset: Boolean = false) {
        if(country.value == null) return
        loadingState.update { NeighbourhoodSelectorScreen.STATE }
        sPage.updateAndGet { it + 1 }
        if(reset || states.isEmpty()) {
            sPage.update { 0 }
            states.clear()
        }
        geoRepository
            .getStates(q = "", countryIso3 = country.value!!.iso3, page = sPage.value)
            .handle {
                if(it == null) return@handle
                states.addAll(it.content)
                cities.clear()
                neighbourhoods.clear()
                loadingState.update { null }
            }
    }

    var kPage = MutableStateFlow(0)
    var countries = mutableStateListOf<Country>()
        private set
    suspend fun updateCountries(reset: Boolean = false) {
        loadingState.update { NeighbourhoodSelectorScreen.COUNTRY }
        kPage.updateAndGet { it + 1 }
        if(reset || countries.isEmpty()) {
            kPage.update { 0 }
            countries.clear()
        }
        geoRepository
            .getCountries(q = "", page = kPage.value)
            .handle {
                if(it == null) return@handle
                countries.addAll(it.content)
                states.clear()
                cities.clear()
                neighbourhoods.clear()
                loadingState.update { null }
            }
    }

    val events: NeighbourhoodFieldLoadEvents = NeighbourhoodFieldDefaults.copy(
        neighbourhoods = neighbourhoods,
        cities = cities,
        states = states,
        countries = countries,

        onNeighbourhoodLoadRequest = { updateNeighbourhoods(it) },

        onCityLoadRequest = { updateCities(it) },
        onCitySelected = { selected -> city.update { selected } },

        onStateLoadRequest = { updateStates(it) },
        onStateSelected = { selected -> state.update { selected } },

        onCountryLoadRequest = { updateCountries(it) },
        onCountrySelected = { selected -> country.update { selected } }

    )



}