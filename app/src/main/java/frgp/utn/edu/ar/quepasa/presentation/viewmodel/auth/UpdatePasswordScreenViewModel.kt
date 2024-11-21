package frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.error
import frgp.utn.edu.ar.quepasa.data.dto.handle
import frgp.utn.edu.ar.quepasa.data.dto.onValidationError
import frgp.utn.edu.ar.quepasa.data.model.auth.TotpDetails
import frgp.utn.edu.ar.quepasa.domain.context.feedback.Feedback
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import quepasa.api.validators.users.PasswordValidator
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val NEWPASSWORD_ID = "password@UpdatePassword"
    val REPEATPASSWORD_ID = "repeatPassword@UpdatePassword"

    val error = MutableSharedFlow<String>(1)
    val s = MutableSharedFlow<String>(1)
    val feedback = MutableStateFlow<Feedback?>(null)

    val newPassword = MutableStateFlow("")
    val valid_newPassword = MutableStateFlow(false)
    fun updateNewPassword(value: String, valid: Boolean?) {
        newPassword.update { value }
        if(valid != null) {
            if(feedback.value?.field.equals(NEWPASSWORD_ID)) feedback.update { null }
            valid_newPassword.update { valid }
        }
    }

    val repeatNewPassword = MutableStateFlow("")
    val valid_repeatPassword = MutableStateFlow(false)
    fun updateRepeatPassword(value: String, valid: Boolean?) {
        repeatNewPassword.update { value }
        if(valid != null) {
            if (feedback.value?.field.equals(REPEATPASSWORD_ID)) feedback.update { null }
            valid_repeatPassword.update { valid }
        }
    }

    fun newPasswordValidator(value: String): PasswordValidator {
        return PasswordValidator(value)
            .ifNullThen("")
            .isNotBlank()
            .hasOneDigit()
            .hasOneUpperCaseLetter()
            .hasOneLowerCaseLetter()
            .hasOneSpecialCharacter()
            .lengthIsEightCharactersOrMore()
    }

    fun repeatPasswordValidator(value: String): PasswordValidator {
        return PasswordValidator(value)
            .equals(newPassword.value, "Las contraseñas no coinciden. ")
    }

    val loading = MutableStateFlow<Boolean>(false)
    val err = MutableStateFlow<Fail?>(null)

    suspend fun request() {
        loading.update { true }
        userRepository
            .updateMyPassword(newPassword.value)
            .handle {
                newPassword.update { "" }
                repeatNewPassword.update { "" }
                s.tryEmit("La contraseña fue actualizada exitosamente. ")
                loading.update { false }
            }
            .onValidationError { e ->
                feedback.update { Feedback(
                    field = NEWPASSWORD_ID,
                    message = e.errors.first()
                ) }
                loading.update { false }
            }
            .error { e ->
                Log.d("UPDATE_PASSWORD", "Err is null: " + (e == null).toString() + "; message = " + if(e == null) "" else e.message)
                err.update { null }
                err.update { e }
                loading.update { false }
            }
    }

    suspend fun reload() {
        loading.update { true }
        err.tryEmit(null)
    }

}