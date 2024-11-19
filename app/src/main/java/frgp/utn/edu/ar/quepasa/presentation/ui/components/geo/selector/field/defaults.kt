package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.field

import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.modal.NeighbourhoodSelectorScreen

interface GeoFieldCountryDataEvents {
    val countries: List<Country>
    val onCountrySelected: (Country) -> Unit
    val onCountryLoadRequest: suspend (Boolean) -> Unit
}

interface GeoFieldStateDataEvents {
    val states: List<SubnationalDivision>
    val onStateSelected: (SubnationalDivision) -> Unit
    val onStateLoadRequest: suspend (Boolean) -> Unit
}


interface GeoFieldCityDataEvents {
    val cities: List<City>
    val onCitySelected: (City) -> Unit
    val onCityLoadRequest: suspend (Boolean) -> Unit
}

interface GeoFieldEditable<T> {
    val value: Set<T>
    val onSelect: (T) -> Unit
}

interface NeighbourhoodFieldLoadEvents: GeoFieldCityDataEvents, GeoFieldStateDataEvents, GeoFieldCountryDataEvents {
    val onNeighbourhoodLoadRequest: suspend (Boolean) -> Unit
    val isLoading: NeighbourhoodSelectorScreen?
    val neighbourhoods: List<Neighbourhood>
}


object NeighbourhoodFieldDefaults: NeighbourhoodFieldLoadEvents {
    override val neighbourhoods: List<Neighbourhood>
        get() = emptyList()
    override val onNeighbourhoodLoadRequest: suspend (Boolean) -> Unit
        get() = {  }
    override val cities: List<City>
        get() = emptyList()
    override val onCitySelected: (City) -> Unit
        get() = {  }
    override val onCityLoadRequest: suspend (Boolean) -> Unit
        get() = {  }
    override val states: List<SubnationalDivision>
        get() = emptyList()
    override val onStateSelected: (SubnationalDivision) -> Unit
        get() = {  }
    override val onStateLoadRequest: suspend (Boolean) -> Unit
        get() = {  }
    override val countries: List<Country>
        get() = emptyList()
    override val onCountrySelected: (Country) -> Unit
        get() = {  }
    override val onCountryLoadRequest: suspend (Boolean) -> Unit
        get() = {  }
    override val isLoading: NeighbourhoodSelectorScreen?
        get() = null
    fun copy(
        neighbourhoods: List<Neighbourhood> = emptyList(),
        onNeighbourhoodLoadRequest: suspend (Boolean) -> Unit = this.onNeighbourhoodLoadRequest,
        cities: List<City> = this.cities,
        onCitySelected: (City) -> Unit = this.onCitySelected,
        onCityLoadRequest: suspend (Boolean) -> Unit = this.onCityLoadRequest,
        states: List<SubnationalDivision> = this.states,
        onStateSelected: (SubnationalDivision) -> Unit = this.onStateSelected,
        onStateLoadRequest: suspend (Boolean) -> Unit = this.onStateLoadRequest,
        countries: List<Country> = this.countries,
        onCountrySelected: (Country) -> Unit = this.onCountrySelected,
        onCountryLoadRequest: suspend (Boolean) -> Unit = this.onCountryLoadRequest,
        isLoading: NeighbourhoodSelectorScreen? = this.isLoading
    ): NeighbourhoodFieldLoadEvents {
        return object : NeighbourhoodFieldLoadEvents {
            override val neighbourhoods: List<Neighbourhood> = neighbourhoods
            override val onNeighbourhoodLoadRequest = onNeighbourhoodLoadRequest
            override val cities = cities
            override val onCitySelected = onCitySelected
            override val onCityLoadRequest = onCityLoadRequest
            override val states = states
            override val onStateSelected = onStateSelected
            override val onStateLoadRequest = onStateLoadRequest
            override val countries = countries
            override val onCountrySelected = onCountrySelected
            override val onCountryLoadRequest = onCountryLoadRequest
            override val isLoading = isLoading
        }
    }
}