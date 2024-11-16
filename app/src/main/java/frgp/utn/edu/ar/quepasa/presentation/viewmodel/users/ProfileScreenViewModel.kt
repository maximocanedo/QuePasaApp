package frgp.utn.edu.ar.quepasa.presentation.viewmodel.users

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.request.CodeVerificationRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.domain.context.feedback.Feedback
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

    var feedback = MutableStateFlow<Feedback?>(null)

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
                if(username.value == null || username.value?.isBlank() != false) {
                    updated = usersRepository.getAuthenticatedUser()
                    _isRefreshing.update { false }
                } else {
                    when(val response = usersRepository.findByUsername(username.value!!)) {
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
        when(val mail = usersRepository.requestMailVerificationCode(mailAddress)) {
            is ApiResponse.Success -> {
                if(mail.data == null || user.value == null) return
                updateUser()
            }
            is ApiResponse.ValidationError -> {
                feedback.update { Feedback(field = "mail", message = mail.details.errors.first()) }
            }
            is ApiResponse.Error -> {
                feedback.update { Feedback(field = "mail", message = mail.exception.message) }
            }
        }
    }

    suspend fun onPhoneRegistrationRequest(phoneNumber: String) {
        when(val phone = usersRepository.requestPhoneVerificationCode(phoneNumber)) {
            is ApiResponse.Success -> {
                if(phone.data == null || user.value == null) return
                updateUser()
            }
            is ApiResponse.ValidationError -> {
                feedback.update { Feedback(field = "phone", message = phone.details.errors.first()) }
            }
            is ApiResponse.Error -> {
                feedback.update { Feedback(field = "phone", message = phone.exception.message) }
            }
        }
    }

    suspend fun onMailValidationRequest(mail: Mail, code: String): Boolean {
        val response = usersRepository.verifyMail(CodeVerificationRequest(
            mail.mail, code
        ))
        return when(response) {
            is ApiResponse.Success -> {
                updateUser()
                true
            }
            is ApiResponse.ValidationError -> {
                feedback.update { Feedback(field = "mailCodeVerification", message = response.details.errors.first()) }
                false
            }
            is ApiResponse.Error -> {
                feedback.update { Feedback(field = "mailCodeVerification", message = response.exception.message) }
                false
            }
        }
    }

    suspend fun onPhoneValidationRequest(phone: Phone, code: String): Boolean {
        val response = usersRepository.verifyPhone(CodeVerificationRequest(
            phone.phone, code
        ))
        return when(response) {
            is ApiResponse.Success -> {
                updateUser()
                true
            }
            is ApiResponse.ValidationError -> {
                feedback.update { Feedback(field = "phoneCodeVerification", message = response.details.errors.first()) }
                false
            }
            is ApiResponse.Error -> {
                feedback.update { Feedback(field = "phoneCodeVerification", message = response.exception.message) }
                false
            }
        }
    }

    suspend fun onMailDeleteRequest(mail: Mail) {
        val response = usersRepository.deleteMail(mail.mail)
        when(response) {
            is ApiResponse.Success -> {
                updateUser()
            }
            is ApiResponse.ValidationError -> {
                // Error.
            }
            is ApiResponse.Error -> {
                // Error
            }
        }
    }

    suspend fun onPhoneDeleteRequest(phone: Phone) {
        val response = usersRepository.deletePhone(phone.phone)
        when(response) {
            is ApiResponse.Success -> {
                updateUser()
            }
            is ApiResponse.ValidationError -> {
                // Error.
            }
            is ApiResponse.Error -> {
                // Error
            }
        }
    }

}