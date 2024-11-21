package frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostFormViewModel: ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _audience = MutableStateFlow("PUBLIC")
    val audience: StateFlow<String> get() = _audience

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    private val _neighbourhood = MutableStateFlow(0L)
    val neighbourhood: StateFlow<Long> get() = _neighbourhood

    private val _type = MutableStateFlow(0)
    val type: StateFlow<Int> get() = _type

    private val _subtype = MutableStateFlow(0)
    val subtype: StateFlow<Int> get() = _subtype

    private val _tag = MutableStateFlow("")
    val tag: StateFlow<String> get() = _tag

    private val _tags = MutableStateFlow<List<String>>(emptyList())
    val tags: StateFlow<List<String>> get() = _tags

    private var _tagCount = MutableStateFlow(0)

    private val _fieldsValidation: List<MutableStateFlow<Boolean>> = List(2) { MutableStateFlow(false) }

    private val _fieldTagValidation = MutableSharedFlow<Boolean>()

    fun getTitle(): String {
        return title.value
    }

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun getAudience(): String {
        return audience.value
    }

    fun updateAudience(audience: String) {
        _audience.value = audience
    }

    fun getSubtype(): Int {
        return subtype.value
    }

    fun updateSubtype(subtype: Int) {
        _subtype.value = subtype
    }

    fun getNeighbourhood(): Long {
        return neighbourhood.value
    }

    fun updateNeighbourhood(neighbourhood: Long) {
        _neighbourhood.value = neighbourhood
    }

    fun getDescription(): String {
        return description.value
    }

    fun updateDescription(description: String) {
        _description.value = description
    }

    fun getTag(): String {
        return tag.value
    }

    fun updateTag(tag: String) {
        _tag.value = tag
    }

    fun getTags(): List<String> {
        return _tags.value
    }

    fun addTag(tag: String) {
        if(_tagCount.value < 5) {
            val tags = _tags.value + tag
            _tags.value = tags
            _tagCount.value = _tags.value.size
        }
    }

    fun removeTag(tag: String) {
        var tags: List<String> = emptyList()
        _tags.value.forEach { tagValue ->
            if(tagValue != tag) tags = tags + tagValue
        }
        _tags.value = tags
        _tagCount.value = _tags.value.size
    }

    private fun clearTags() {
        _tags.value = emptyList()
        _tagCount.value = 0
    }

    fun toggleValidationField(index: Int, state: Boolean) {
        require(index in _fieldsValidation.indices) { "Index out of bounds" }
        _fieldsValidation[index].value = state
    }

    fun checkValidationFields(): Boolean {
        return _fieldsValidation.all { it.value }
    }

    fun toggleTagValidationField(state: Boolean) {
        viewModelScope.launch {
            _fieldTagValidation.emit(state)
        }
    }
}