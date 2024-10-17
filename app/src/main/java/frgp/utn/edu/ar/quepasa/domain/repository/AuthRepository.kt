package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.LoginRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetAttempt
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.AuthenticationResponse
import frgp.utn.edu.ar.quepasa.data.model.auth.SingleUseRequest
import frgp.utn.edu.ar.quepasa.data.source.remote.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val apiResponseHandler: ApiResponseHandler
) {

    suspend fun signUp(request: SignUpRequest): ApiResponse<AuthenticationResponse?> {
        val raw = authService.signup(request)
        val response = apiResponseHandler.getResponse(raw)
        return response
    }

    suspend fun login(request: LoginRequest): ApiResponse<AuthenticationResponse?> {
        return apiResponseHandler.getResponse(authService.login(request))
    }

    suspend fun loginUsingTotp(code: String): ApiResponse<AuthenticationResponse?> {
        return apiResponseHandler.getResponse(authService.loginUsingTotp(code))
    }

    suspend fun requestPasswordReset(request: PasswordResetRequest): ApiResponse<SingleUseRequest?> {
        return apiResponseHandler.getResponse(authService.requestPasswordReset(request))
    }

    suspend fun resetPassword(request: PasswordResetAttempt): ApiResponse<AuthenticationResponse?> {
        return apiResponseHandler.getResponse(authService.resetPassword(request))
    }

}