package frgp.utn.edu.ar.quepasa.domain.repository

import android.util.Log
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.model.Trend
import frgp.utn.edu.ar.quepasa.data.source.remote.TrendService
import retrofit2.Response
import javax.inject.Inject

class TrendRepository @Inject constructor(
    private val trendService: TrendService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T & Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }
    suspend fun getTrends(barrio: Int, fechaBase: String): List<Trend> =
        handleResponse { trendService.getTrends(barrio, fechaBase) }
}
