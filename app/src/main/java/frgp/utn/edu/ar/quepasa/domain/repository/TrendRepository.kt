package frgp.utn.edu.ar.quepasa.domain.repository

import android.util.Log
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.model.Trend
import frgp.utn.edu.ar.quepasa.data.source.remote.TrendService
import javax.inject.Inject

class TrendRepository @Inject constructor(
    private val trendService: TrendService
) {
    suspend fun getTrends(barrio: Int, fechaBase: String): List<Trend> {
        return when (val response = trendService.getTrends(barrio, fechaBase)) {
            is ApiResponse.Success<*> -> {
                val data = response.data as? List<Trend> ?: emptyList()
                Log.d("TrendRepository", "trends: $data")
                data
            }
            is ApiResponse.ValidationError -> {
                Log.e("TrendRepository", "error: ${response.details}")
                emptyList()
            }
            is ApiResponse.Error -> {
                Log.e("TrendRepository", "error: ${response.exception}")
                emptyList()
            }
        }
    }
}
