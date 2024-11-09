package frgp.utn.edu.ar.quepasa.domain.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.LoginRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetAttempt
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.AuthenticationResponse
import frgp.utn.edu.ar.quepasa.data.model.auth.SingleUseRequest
import frgp.utn.edu.ar.quepasa.data.source.remote.AuthService
import frgp.utn.edu.ar.quepasa.data.source.remote.saveAuthToken
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val apiResponseHandler: ApiResponseHandler,
    @ApplicationContext private val context: Context
) {

    suspend fun signUp(request: SignUpRequest): ApiResponse<AuthenticationResponse?> {
        val raw = authService.signup(request)
        val response = apiResponseHandler.getResponse(raw)
        if(response is ApiResponse.Success) {
            val token = response.data?.token ?: ""
            if(token.isNotBlank()) {
                saveAuthToken(context, token)
            }
        }
        return response
    }

    suspend fun login(request: LoginRequest): ApiResponse<AuthenticationResponse?> {
        val x = apiResponseHandler.getResponse(authService.login(request))
        if(x is ApiResponse.Success) {
            val token = x.data?.token ?: ""
            if(token.isNotBlank()) {
                saveAuthToken(context, token)
            }
        }
        return x
    }

    suspend fun loginUsingTotp(code: String): ApiResponse<AuthenticationResponse?> {
        val mediaType = "text/plain".toMediaTypeOrNull()
        val requestBody = code.toRequestBody(mediaType)
        val x = apiResponseHandler.getResponse(authService.loginUsingTotp(requestBody))
        if(x is ApiResponse.Success) {
            val token = x.data?.token ?: ""
            if(token.isNotBlank()) {
                saveAuthToken(context, token)
            }
        }
        return x
    }

    suspend fun requestPasswordReset(request: PasswordResetRequest): ApiResponse<SingleUseRequest?> {
        return apiResponseHandler.getResponse(authService.requestPasswordReset(request))
    }

    suspend fun resetPassword(request: PasswordResetAttempt): ApiResponse<AuthenticationResponse?> {
        return apiResponseHandler.getResponse(authService.resetPassword(request))
    }

}