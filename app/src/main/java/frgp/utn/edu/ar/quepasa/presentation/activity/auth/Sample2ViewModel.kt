package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import javax.inject.Inject;

import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import quepasa.api.validators.users.PasswordValidator
import quepasa.api.validators.users.UsernameValidator

@HiltViewModel
open class Sample2ViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {


    private val loginUsernameMutable = MutableStateFlow("")
    fun setLoginUsername(x: String) {
        loginUsernameMutable.value = x
    }
    val loginUsername = loginUsernameMutable.asStateFlow()



    private val signupNameMutable = MutableStateFlow("")
    fun setSignupName(x: String) {
        signupNameMutable.value = x
    }
    val signupName = signupNameMutable.asStateFlow()
    private val nameIsValidMutable = MutableStateFlow(false)
    val nameIsValid = nameIsValidMutable.asStateFlow()
    fun setNameValidity(x: Boolean) { nameIsValidMutable.value = x }

    private val signupUsernameMutable = MutableStateFlow("")
    fun setSignupUsername(x: String) {
        signupUsernameMutable.value = x
    }
    val signupUsername = signupUsernameMutable.asStateFlow()
    private val usernameIsValidMutable = MutableStateFlow(false)
    val usernameIsValid = usernameIsValidMutable.asStateFlow()
    fun setUsernameValidity(x: Boolean) { usernameIsValidMutable.value = x }

    private val signupPasswordMutable = MutableStateFlow("")
    fun setSignupPassword(x: String) {
        signupPasswordMutable.value = x
    }
    val signupPassword = signupPasswordMutable.asStateFlow()
    private val passwordIsValidMutable = MutableStateFlow(false)
    val passwordIsValid = passwordIsValidMutable.asStateFlow()
    fun setPasswordValidity(x: Boolean) { passwordIsValidMutable.value = x }

    private val signupPasswordRepeatableMutable = MutableStateFlow("")
    fun setSignupRepeatablePassword(x: String) {
        signupPasswordRepeatableMutable.value = x
    }
    val signupPasswordRepeatable = signupPasswordRepeatableMutable.asStateFlow()
    private val passwordRepeatableIsValidMutable = MutableStateFlow(false)
    val passwordRepeatableIsValid = passwordRepeatableIsValidMutable.asStateFlow()
    fun setRepeatablePasswordValidity(x: Boolean) { passwordRepeatableIsValidMutable.value = x }

    open fun usernameValidator(username: String): UsernameValidator {
        return UsernameValidator(username)
            .isAvailable(userRepository)
            .meetsMaximumLength()
            .meetsMinimumLength()
            .neitherStartsNorEndsWithDoubleDotsOrUnderscores()
            .doesntHaveIllegalCharacters()
            .doesntHaveTwoDotsOrUnderscoresInARow()
    }
    open fun passwordValidator(password: String): PasswordValidator {
        return PasswordValidator(password)
            .ifNullThen("")
            .isNotBlank()
            .hasOneDigit()
            .lengthIsEightCharactersOrMore()
            .hasOneLowerCaseLetter()
            .hasOneSpecialCharacter()
            .hasOneUpperCaseLetter()
    }
    open fun passwordRepeatValidator(password: String, original: String): PasswordValidator {
        return PasswordValidator(password)
            .equals(original)
    }

    suspend fun signUp() {
        if(!usernameIsValid.value || !passwordIsValid.value || !passwordRepeatableIsValid.value)
            return
        var req = SignUpRequest(signupName.value, signupUsername.value, signupPassword.value)
        var res = authRepository.signUp(req)
        when(res) {
            is ApiResponse.Success -> {

            },
            is ApiResponse.ValidationError -> {

            },
            is ApiResponse
        }
    }
}
