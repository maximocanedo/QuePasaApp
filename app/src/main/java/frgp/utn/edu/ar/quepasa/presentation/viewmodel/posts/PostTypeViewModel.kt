package frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.PostType
import frgp.utn.edu.ar.quepasa.domain.repository.PostTypeRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostTypeViewModel @Inject constructor(
    private val repository: PostTypeRepository
) : ViewModel() {
    private val _postTypes = MutableStateFlow<Page<PostType>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val postTypes: StateFlow<Page<PostType>> get() = _postTypes

    private val _postType = MutableStateFlow<PostType?>(null)
    val postType: StateFlow<PostType?> get() = _postType

    private val _postTypeSel = MutableStateFlow<PostType?>(null)
    val postTypeSel: StateFlow<PostType?> get() = _postTypeSel

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        viewModelScope.launch {
            getTypes(0, 10,  true)
        }
    }

    suspend fun getTypes(page: Int, size: Int, activeOnly: Boolean) {
        try {
            val types = repository.getTypes(page, size, activeOnly)
            _postTypes.value = types
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getTypes(q: String, sort: String, page: Int, size: Int, active: Boolean) {
        try {
            val types = repository.getTypes(q, sort, page, size, active)
            _postTypes.value = types
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getTypeById(id: Int) {
        try {
            val type = repository.getTypeById(id)
            _postType.value = type
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getTypesBySubtype(id: Int, page: Int, size: Int) {
        try {
            val types = repository.getTypesBySubtype(id, page, size)
            _postTypes.value = types
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun createType(description: String) {
        try {
            val newType = repository.createType(description)
            _postType.value = newType
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun updateType(id: Int, description: String) {
        try {
            val newType = repository.updateType(id, description)
            _postType.value = newType
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deleteType(id: Int) {
        try {
            repository.deleteType(id)
            getTypes(0, 10, true)
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }
}