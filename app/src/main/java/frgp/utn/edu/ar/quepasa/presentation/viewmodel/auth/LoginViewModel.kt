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
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val _sampleText = MutableLiveData<String>()
    val sampleText: LiveData<String> = _sampleText

    fun fetchSampleText() {
        viewModelScope.launch(Dispatchers.IO) {
            _sampleText.postValue("Pantalla de inicio de sesión. ")
        }
    }
}