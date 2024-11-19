package frgp.utn.edu.ar.quepasa.data.source.remote.geo

import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeoService {

    @GET("countries")
    suspend fun getCountries(
        @Query("q") q: String = "",
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<Page<Country>>

    @GET("countries/{iso3}/states")
    suspend fun getStates(
        @Path("iso3") iso3: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("q") q: String = ""
    ): Response<Page<SubnationalDivision>>

    @GET("cities/subdivision/{iso3}")
    suspend fun getCities(
        @Path("iso3") iso3: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("q") q: String = ""
    ): Response<Page<City>>

    @GET("neighbourhoods/search")
    suspend fun getNeighbourhoods(
        @Query("city") city: Long = -1,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("q") q: String = ""
    ): Response<Page<Neighbourhood>>

}