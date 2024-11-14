package frgp.utn.edu.ar.quepasa.presentation.viewmodel.users

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.request.CodeVerificationRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val usersRepository: UserRepository
): ViewModel() {

    var username = MutableStateFlow<String?>(null)
        private set

    var user = MutableStateFlow<User?>(null)
        private set

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    var error = MutableSharedFlow<Fail?>(1)
        private set

    fun updateUser(user: User? = null) {
        _isRefreshing.update { true }
        CoroutineScope(IO).launch {
            if(user == null) {
                var updated: User? = null
                if(username.value == null || username.value?.isBlank() ?: true) {
                    updated = usersRepository.getAuthenticatedUser()
                    _isRefreshing.update { false }
                } else {
                    val response = usersRepository.findByUsername(username.value!!)
                    when(response) {
                        is ApiResponse.Success -> {
                            updated = response.data
                        }
                        is ApiResponse.ValidationError -> {}
                        is ApiResponse.Error -> {
                            error.tryEmit(response.exception)
                        }
                    }
                }

                this@ProfileScreenViewModel.user.tryEmit(updated)
                _isRefreshing.update { false }
            } else {
                this@ProfileScreenViewModel.user.update { user }
                _isRefreshing.update { false }
            }
        }
    }

    suspend fun onMailRegistrationRequest(mailAddress: String) {
        val mail = usersRepository.requestMailVerificationCode(mailAddress)
        when(mail) {
            is ApiResponse.Success -> {
                if(mail.data == null || user.value == null) return
                val currentUser = user.value!!
                val updatedUser = currentUser.copy(
                    email = currentUser.email + mail.data
                )
                updateUser(updatedUser)
            }
            is ApiResponse.ValidationError -> {

            }
            is ApiResponse.Error -> {

            }
        }
    }

    suspend fun onMailValidationRequest(mail: Mail, code: String): Boolean {
        val response = usersRepository.verifyMail(CodeVerificationRequest(
            mail.mail, code
        ))
        return when(response) {
            is ApiResponse.Success -> {
                true
            }

            is ApiResponse.ValidationError -> {

                false
            }

            is ApiResponse.Error -> {

                false
            }
        }
    }

    suspend fun onMailDeleteRequest(mail: Mail) {
        // Sin servicio hasta que la issue #125 esté resuelta.
    }

}