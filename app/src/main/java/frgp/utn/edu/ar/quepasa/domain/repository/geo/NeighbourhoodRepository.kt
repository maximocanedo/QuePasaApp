package frgp.utn.edu.ar.quepasa.domain.repository.geo

import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.source.remote.geo.NeighbourhoodService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
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

    suspend fun getNeighbourhoods(
        activeOnly: Boolean,
        page: Int = 0,
        size: Int = 10
    ): Page<Neighbourhood> =
        handleResponse { neighbourhoodService.getNeighbourhoods(activeOnly, page, size) }

    suspend fun getNeighbourhoodById(id: Long): Neighbourhood =
        handleResponse { neighbourhoodService.getNeighbourhoodById(id) }

    suspend fun getNeighbourhoodsByName(
        q: String,
        page: Int = 0,
        size: Int = 10
    ): Page<Neighbourhood> =
        handleResponse { neighbourhoodService.getNeighbourhoodsByName(q, page, size) }

    suspend fun createNeighbourhood(neighbourhood: Neighbourhood): Neighbourhood =
        handleResponse { neighbourhoodService.createNeighbourhood(neighbourhood) }

    suspend fun updateNeighbourhood(id: Long, neighbourhood: Neighbourhood): Neighbourhood =
        handleResponse { neighbourhoodService.updateNeighbourhood(id, neighbourhood) }

    suspend fun deleteNeighbourhood(id: Long) =
        handleResponse<Void> { neighbourhoodService.deleteNeighbourhood(id) }
}