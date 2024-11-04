package frgp.utn.edu.ar.quepasa.domain.repository

import android.util.Log
import frgp.utn.edu.ar.quepasa.data.model.Trend
import frgp.utn.edu.ar.quepasa.data.source.remote.TrendService
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject

class TrendRepository @Inject constructor(
    private val trendService: TrendService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()
        } else {
            Log.e("TrendRepository", "Error en la respuesta: ${response.message()}")
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }

    suspend fun getTrends(barrio: Int, fechaBase: LocalDateTime): List<Trend> =
        handleResponse { trendService.getTrends(barrio, fechaBase) } ?: emptyList()
}


