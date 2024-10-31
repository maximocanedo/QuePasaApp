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
    private val repository: AuthRepository,
    private val users: UserRepository
) : ViewModel() {

    private val _sampleText = MutableLiveData<String>()
    val sampleText: LiveData<String> = _sampleText

    // Método que actualiza sampleText con una corrutina
    fun fetchSampleText() {
        viewModelScope.launch(Dispatchers.IO) {
            val availability = "${users.checkUsernameAvailability("root")} + ${users.checkUsernameAvailability("jglfkgjlakfjasldkj")}"
            _sampleText.postValue("Hallo, Hola! $availability")
        }
    }
}