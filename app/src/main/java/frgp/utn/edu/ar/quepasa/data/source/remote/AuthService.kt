package frgp.utn.edu.ar.quepasa.data.source.remote

import android.content.Context
import frgp.utn.edu.ar.quepasa.data.dto.request.LoginRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetAttempt
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.AuthenticationResponse
import frgp.utn.edu.ar.quepasa.data.model.auth.SingleUseRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthenticationResponse>

    @POST("signup")
    suspend fun signup(@Body request: SignUpRequest): Response<AuthenticationResponse>

    @POST("login/totp")
    suspend fun loginUsingTotp(@Body code: String): Response<AuthenticationResponse>

    @POST("recover")
    suspend fun requestPasswordReset(@Body request: PasswordResetRequest): Response<SingleUseRequest>

    @POST("recover/reset")
    suspend fun resetPassword(@Body request: PasswordResetAttempt): Response<AuthenticationResponse>

}

fun getAuthToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("QuePasaToken", Context.MODE_PRIVATE)
    return sharedPreferences.getString("identity", null)
}

fun saveAuthToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("QuePasaToken", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("identity", token)
    editor.apply()
}