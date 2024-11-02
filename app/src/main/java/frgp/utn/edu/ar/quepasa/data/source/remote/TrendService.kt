package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.model.Trend
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

interface TrendService {

    @GET("api/trends/{barrio}")
    suspend fun getTrends(
        @Path("barrio") barrio: Int,
        @Query("fechaBase") fechaBase: LocalDateTime
    ): Response<List<Trend>>
}







