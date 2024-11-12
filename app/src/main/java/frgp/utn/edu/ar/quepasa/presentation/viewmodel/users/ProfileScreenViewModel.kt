package frgp.utn.edu.ar.quepasa.presentation.viewmodel.users

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.CodeVerificationRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val usersRepository: UserRepository
): ViewModel() {

    var user = MutableStateFlow<User?>(null)
        private set

    fun updateUser(user: User? = null) {
        CoroutineScope(IO).launch {
            if(user == null) {
                val updated = usersRepository.getAuthenticatedUser()
                this@ProfileScreenViewModel.user.tryEmit(updated)
            } else {
                this@ProfileScreenViewModel.user.tryEmit(user)
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
                user.emit(updatedUser)
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