package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.field

import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.modal.NeighbourhoodSelectorScreen

interface GeoFieldCountryDataEvents {
    val countries: List<Country>
    val onCountrySelected: (Country) -> Unit
    val onCountryLoadRequest: suspend () -> Unit
}

interface GeoFieldStateDataEvents {
    val states: List<SubnationalDivision>
    val onStateSelected: (SubnationalDivision) -> Unit
    val onStateLoadRequest: suspend () -> Unit
}


interface GeoFieldCityDataEvents {
    val cities: List<City>
    val onCitySelected: (City) -> Unit
    val onCityLoadRequest: suspend () -> Unit
}

interface GeoFieldEditable<T> {
    val value: Set<T>
    val onSelect: (T) -> Unit
}

interface NeighbourhoodFieldLoadEvents: GeoFieldCityDataEvents, GeoFieldStateDataEvents, GeoFieldCountryDataEvents {
    val neighbourhoods: List<Neighbourhood>
    val onNeighbourhoodSelect: (Neighbourhood) -> Unit
    val onNeighbourhoodUnselect: (Neighbourhood) -> Unit
    val onNeighbourhoodLoadRequest: suspend () -> Unit
    val isLoading: NeighbourhoodSelectorScreen?
}


object NeighbourhoodFieldDefaults: NeighbourhoodFieldLoadEvents {
    override val neighbourhoods: List<Neighbourhood>
        get() = emptyList()
    override val onNeighbourhoodSelect: (Neighbourhood) -> Unit
        get() = {  }
    override val onNeighbourhoodUnselect: (Neighbourhood) -> Unit
        get() = {  }
    override val onNeighbourhoodLoadRequest: suspend () -> Unit
        get() = {  }
    override val cities: List<City>
        get() = emptyList()
    override val onCitySelected: (City) -> Unit
        get() = {  }
    override val onCityLoadRequest: suspend () -> Unit
        get() = {  }
    override val states: List<SubnationalDivision>
        get() = emptyList()
    override val onStateSelected: (SubnationalDivision) -> Unit
        get() = {  }
    override val onStateLoadRequest: suspend () -> Unit
        get() = {  }
    override val countries: List<Country>
        get() = emptyList()
    override val onCountrySelected: (Country) -> Unit
        get() = {  }
    override val onCountryLoadRequest: suspend () -> Unit
        get() = {  }
    override val isLoading: NeighbourhoodSelectorScreen?
        get() = null
    fun copy(
        neighbourhoods: List<Neighbourhood> = this.neighbourhoods,
        onNeighbourhoodSelect: (Neighbourhood) -> Unit = this.onNeighbourhoodSelect,
        onNeighbourhoodUnselect: (Neighbourhood) -> Unit = this.onNeighbourhoodUnselect,
        onNeighbourhoodLoadRequest: suspend () -> Unit = this.onNeighbourhoodLoadRequest,
        cities: List<City> = this.cities,
        onCitySelected: (City) -> Unit = this.onCitySelected,
        onCityLoadRequest: suspend () -> Unit = this.onCityLoadRequest,
        states: List<SubnationalDivision> = this.states,
        onStateSelected: (SubnationalDivision) -> Unit = this.onStateSelected,
        onStateLoadRequest: suspend () -> Unit = this.onStateLoadRequest,
        countries: List<Country> = this.countries,
        onCountrySelected: (Country) -> Unit = this.onCountrySelected,
        onCountryLoadRequest: suspend () -> Unit = this.onCountryLoadRequest,
        isLoading: NeighbourhoodSelectorScreen? = this.isLoading
    ): NeighbourhoodFieldLoadEvents {
        return object : NeighbourhoodFieldLoadEvents {
            override val neighbourhoods = neighbourhoods
            override val onNeighbourhoodSelect = onNeighbourhoodSelect
            override val onNeighbourhoodUnselect = onNeighbourhoodUnselect
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