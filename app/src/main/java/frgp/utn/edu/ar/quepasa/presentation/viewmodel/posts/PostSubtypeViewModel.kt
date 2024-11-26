package frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.request.PostSubtypeRequest
import frgp.utn.edu.ar.quepasa.data.model.PostSubtype
import frgp.utn.edu.ar.quepasa.domain.repository.PostSubtypeRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostSubtypeViewModel @Inject constructor(
    private val repository: PostSubtypeRepository
) : ViewModel() {
    private val _postSubtypes = MutableStateFlow<Page<PostSubtype>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val postSubtypes: StateFlow<Page<PostSubtype>> get() = _postSubtypes

    private val _postSubtype = MutableStateFlow<PostSubtype?>(null)
    val postSubtype: StateFlow<PostSubtype?> get() = _postSubtype

    private val _postSubtypeSel = MutableStateFlow<PostSubtype?>(null)
    val postSubtypeSel: StateFlow<PostSubtype?> get() = _postSubtypeSel

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        viewModelScope.launch {
            getSubtypes(0, 10, true)
        }
    }

    private suspend fun getSubtypes(page: Int, size: Int, activeOnly: Boolean) {
        try {
            val subtypes = repository.getSubtypes(page, size, activeOnly)
            _postSubtypes.value = subtypes
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getSubtypes(q: String, sort: String, page: Int, size: Int, active: Boolean) {
        try {
            val subtypes = repository.getSubtypes(q, sort, page, size, active)
            _postSubtypes.value = subtypes
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getSubtypeById(id: Int) {
        try {
            val subtype = repository.getSubtypeById(id)
            _postSubtype.value = subtype
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getSubtypesByType(id: Int, page: Int, size: Int) {
        try {
            val subtypes = repository.getSubtypesByType(id, page, size)
            _postSubtypes.value = subtypes
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getSubtypesBySelectedFirst(idType: Int, idSubtype: Int, page: Int, size: Int) {
        try {
            val subtypes = repository.getSubtypesByType(idType, page, size)
            val subtypesSelFirst: MutableList<PostSubtype> = mutableListOf()

            subtypes.content.firstOrNull { it.id == idSubtype }?.let { selectedSubtype ->
                subtypesSelFirst.add(selectedSubtype)
            }

            subtypes.content.filter { it.id != idSubtype }.forEach { subtype ->
                subtypesSelFirst.add(subtype)
            }

            _postSubtypes.value.content = subtypesSelFirst
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun getSubtypeByType(id: Int): Int {
        try {
            _postSubtypes.value.content.forEach { subtype ->
                if(subtype.type.id == id) {
                    return subtype.id
                }
            }
            return 1
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
            return 1
        }
    }

    fun getSubtypeFirstId(): Int {
        return _postSubtypes.value.content[0].id
    }

    fun getSubtypeFirstDescription(): String {
        return _postSubtypes.value.content[0].description
    }

    suspend fun createSubtype(request: PostSubtypeRequest) {
        try {
            val newSubtype = repository.createSubtype(request)
            _postSubtype.value = newSubtype
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun updateSubtype(id: Int, request: PostSubtypeRequest) {
        try {
            val newSubtype = repository.updateSubtype(id, request)
            _postSubtype.value = newSubtype
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deleteSubtype(id: Int) {
        try {
            repository.deleteSubtype(id)
            getSubtypes(0, 10, true)
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }
}