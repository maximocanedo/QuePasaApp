package frgp.utn.edu.ar.quepasa.domain.repository.geo

import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.data.source.remote.geo.GeoService
import frgp.utn.edu.ar.quepasa.domain.repository.process
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import javax.inject.Inject

class GeoRepository @Inject constructor (
    private val geoService: GeoService
) {

    suspend fun getCountries(
        q: String = "",
        page: Int = 0,
        size: Int = 10
    ): ApiResponse<Page<Country>?>
        = geoService.getCountries(q, page, size).process()

    suspend fun getStates(
        q: String = "",
        countryIso3: String,
        page: Int = 0,
        size: Int = 10
    ): ApiResponse<Page<SubnationalDivision>?>
        = geoService.getStates(countryIso3, page, size, q).process()

    suspend fun getCities(
        q: String = "",
        stateIso3: String,
        page: Int = 0,
        size: Int = 10
    ): ApiResponse<Page<City>?>
        = geoService.getCities(stateIso3, page, size, q).process()

    suspend fun getNeighbourhoods(
        q: String = "",
        cityId: Long = -1,
        page: Int = 0,
        size: Int = 10
    ): ApiResponse<Page<Neighbourhood>?>
        = geoService.getNeighbourhoods(cityId, page, size, q).process()

}