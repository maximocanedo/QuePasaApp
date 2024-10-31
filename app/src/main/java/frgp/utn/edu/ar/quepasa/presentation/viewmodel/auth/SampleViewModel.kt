package frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun getSampleText(): String = "Hallo, Hola!"

}