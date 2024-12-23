package frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val auth: AuthRepository,
    private val users: UserRepository
) : ViewModel() {

    private val _sampleText = MutableLiveData<String>()
    val sampleText: LiveData<String> = _sampleText

    fun fetchSampleText() {
        viewModelScope.launch(Dispatchers.IO) {
            var u = users.getAuthenticatedUser()
            val availability = u?.username ?: "Sin sesión iniciada. "
            _sampleText.postValue("Hallo: $availability")
        }
    }
}