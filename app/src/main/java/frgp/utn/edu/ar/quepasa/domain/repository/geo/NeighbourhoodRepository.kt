package frgp.utn.edu.ar.quepasa.domain.repository.geo

import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.source.remote.geo.NeighbourhoodService
import retrofit2.Response
import javax.inject.Inject

class NeighbourhoodRepository @Inject constructor(
    private val neighbourhoodService: NeighbourhoodService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }

    suspend fun getNeighbourhoods(activeOnly: Boolean) {
        handleResponse { neighbourhoodService.getNeighbourhoods(activeOnly) }
    }

    suspend fun getNeighbourhoodById(id: Long) {
        handleResponse { neighbourhoodService.getNeighbourhoodById(id) }
    }

    suspend fun getNeighbourhoodsByName(name: String) {
        handleResponse { neighbourhoodService.getNeighbourhoodsByName(name) }
    }

    suspend fun createNeighbourhood(neighbourhood: Neighbourhood) {
        handleResponse { neighbourhoodService.createNeighbourhood(neighbourhood) }
    }

    suspend fun updateNeighbourhood(id: Long, neighbourhood: Neighbourhood) {
        handleResponse { neighbourhoodService.updateNeighbourhood(id, neighbourhood) }
    }

    suspend fun deleteNeighbourhood(id: Long) {
        handleResponse<Void> { neighbourhoodService.deleteNeighbourhood(id) }
    }
}