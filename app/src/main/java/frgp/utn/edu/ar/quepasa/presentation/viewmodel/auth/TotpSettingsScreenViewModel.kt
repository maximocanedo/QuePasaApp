package frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.error
import frgp.utn.edu.ar.quepasa.data.dto.handle
import frgp.utn.edu.ar.quepasa.data.model.auth.TotpDetails
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TotpSettingsScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val initValue = TotpDetails(
        enabled = false,
        qr = "",
        url = ""
    )
    val details = MutableStateFlow<TotpDetails>(initValue)
    val loading = MutableStateFlow<Boolean>(false)
    val err = MutableSharedFlow<Fail?>()

    suspend fun reload() {
        loading.update { true }
        err.tryEmit(null)
        userRepository.getTotpDetails()
            .handle { i ->
                if(i == null) return@handle
                details.update { i }
                loading.update { false }
            }
            .error {
                err.tryEmit(it)
                loading.update { false }
            }
    }

    suspend fun enable() {
        loading.update { true }
        err.tryEmit(null)
        userRepository.enableTotp()
            .handle {i ->
                if(i == null) return@handle
                details.update { i }
                loading.update { false }
            }
            .error {
                err.tryEmit(it)
                loading.update { false }
            }
    }

    suspend fun disable() {
        loading.update { true }
        err.tryEmit(null)
        userRepository.disableTotp()
            .handle {
                loading.update { false }
            }
            .error {
                err.tryEmit(it)
                loading.update { false }
            }
        reload()
    }

}