package frgp.utn.edu.ar.quepasa.domain.usecase

import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.AuthenticationResponse
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.users.NameValidator
import quepasa.api.validators.users.PasswordValidator
import quepasa.api.validators.users.UsernameValidator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(request: SignUpRequest): ApiResponse<AuthenticationResponse?> {
        try {
            val username = UsernameValidator(request.username)
                .meetsMaximumLength()
                .meetsMinimumLength()
                .neitherStartsNorEndsWithDoubleDotsOrUnderscores()
                .doesntHaveIllegalCharacters()
                .doesntHaveTwoDotsOrUnderscoresInARow()
                .isAvailable(userRepository)
                .build()
            val name = NameValidator(request.name)
                .validateCompoundNames()
                .build()
            val password = PasswordValidator(request.password)
                .hasOneDigit()
                .hasOneLowerCaseLetter()
                .hasOneUpperCaseLetter()
                .hasOneSpecialCharacter()
                .lengthIsEightCharactersOrMore()
                .build()
            return authRepository.signUp(request)
        } catch (err: ValidationError) {
            return ApiResponse.ValidationError(err)
        }
    }

}