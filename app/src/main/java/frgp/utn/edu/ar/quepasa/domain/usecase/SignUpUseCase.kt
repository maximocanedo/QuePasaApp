package frgp.utn.edu.ar.quepasa.domain.usecase

import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.AuthenticationResponse
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import frgp.utn.edu.ar.quepasa.utils.validators.users.NameValidator
import frgp.utn.edu.ar.quepasa.utils.validators.users.PasswordValidator
import frgp.utn.edu.ar.quepasa.utils.validators.users.UsernameValidator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(request: SignUpRequest): ApiResponse<AuthenticationResponse?> {
        val usernameValidationResult = UsernameValidator(request.username)
            .meetsMaximumLength()
            .meetsMinimumLength()
            .neitherStartsNorEndsWithDoubleDotsOrUnderscores()
            .doesntHaveIllegalCharacters()
            .doesntHaveTwoDotsOrUnderscoresInARow()
            .asValidationError()
        if(usernameValidationResult != null)
            return ApiResponse.ValidationError(usernameValidationResult)
        val nameValidationResult = NameValidator(request.name)
            .validateCompoundNames()
            .asValidationError()
        if(nameValidationResult != null)
            return ApiResponse.ValidationError(nameValidationResult)
        val passwordValidationResult = PasswordValidator(request.password)
            .hasOneDigit()
            .hasOneLowerCaseLetter()
            .hasOneUpperCaseLetter()
            .hasOneSpecialCharacter()
            .lengthIsEightCharactersOrMore()
            .asValidationError()
        if(passwordValidationResult != null)
            return ApiResponse.ValidationError(passwordValidationResult)
        return authRepository.signUp(request)
    }

}