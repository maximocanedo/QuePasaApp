package frgp.utn.edu.ar.quepasa.presentation.viewmodel.neighbourhood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.domain.repository.geo.NeighbourhoodRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeighbourhoodViewModel @Inject constructor(
    private val repository: NeighbourhoodRepository
): ViewModel() {
    private val _neighbourhoods = MutableStateFlow<Page<Neighbourhood>>(
        Page(
            content = emptyList(),
            totalElements = 0,
            totalPages = 0,
            pageNumber = 0
        )
    )
    val neighbourhoods: StateFlow<Page<Neighbourhood>> get() = _neighbourhoods

    private val _neighbourhood = MutableStateFlow<Neighbourhood?>(null)
    val neighbourhood: StateFlow<Neighbourhood?> get() = _neighbourhood

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        viewModelScope.launch {
            getNeighbourhoods(true)
        }
    }

    private suspend fun getNeighbourhoods(activeOnly: Boolean, page: Int = 0, size: Int = 10) {
        try {
            val neighbourhoods = repository.getNeighbourhoods(activeOnly, page, size)
            _neighbourhoods.value = neighbourhoods
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getNeighbourhoodById(id: Long) {
        try {
            val neighbourhood = repository.getNeighbourhoodById(id)
            _neighbourhood.value = neighbourhood
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getNeighbourhoodsByName(name: String, page: Int = 0, size: Int = 10) {
        try {
            val neighbourhoods = repository.getNeighbourhoodsByName(name, page, size)
            _neighbourhoods.value = neighbourhoods
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getNeighbourhoodsBySelectedFirst(idNeigh: Long) {
        try {
            val neighbourhoods = repository.getNeighbourhoods(true, 0, 10)
            val neighbourhoodsSelFirst: MutableList<Neighbourhood> = mutableListOf()

            neighbourhoods.content.firstOrNull { it.id == idNeigh }?.let { selectedNeighbourhood ->
                neighbourhoodsSelFirst.add(selectedNeighbourhood)
            }

            neighbourhoods.content.filter { it.id != idNeigh }.forEach { neighbourhood ->
                neighbourhoodsSelFirst.add(neighbourhood)
            }

            _neighbourhoods.value.content = neighbourhoodsSelFirst
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun getNeighbourhoodFirstId(): Long {
        return _neighbourhoods.value.content[0].id
    }

    fun getNeighbourhoodFirstName(): String {
        return _neighbourhoods.value.content[0].name
    }

    suspend fun createNeighbourhood(request: Neighbourhood) {
        try {
            val neighbourhood = repository.createNeighbourhood(request)
            _neighbourhood.value = neighbourhood
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun updateNeighbourhood(id: Long, request: Neighbourhood) {
        try {
            val neighbourhood = repository.updateNeighbourhood(id, request)
            _neighbourhood.value = neighbourhood
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deleteNeighbourhood(id: Long) {
        try {
            repository.deleteNeighbourhood(id)
            getNeighbourhoods(true)
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }
}