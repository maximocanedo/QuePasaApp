package frgp.utn.edu.ar.quepasa.data.source.remote

import android.content.Context
import android.util.Log
import frgp.utn.edu.ar.quepasa.data.dto.request.LoginRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetAttempt
import frgp.utn.edu.ar.quepasa.data.dto.request.PasswordResetRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.SignUpRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.AuthenticationResponse
import frgp.utn.edu.ar.quepasa.data.model.auth.SingleUseRequest
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthenticationResponse>

    @POST("signup")
    suspend fun signup(@Body request: SignUpRequest): Response<AuthenticationResponse>

    @POST("login/totp")
    suspend fun loginUsingTotp(@Body code: RequestBody): Response<AuthenticationResponse>

    @POST("recover")
    suspend fun requestPasswordReset(@Body request: PasswordResetRequest): Response<SingleUseRequest>

    @POST("recover/reset")
    suspend fun resetPassword(@Body request: PasswordResetAttempt): Response<AuthenticationResponse>

}

fun getAuthToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("QuePasaToken", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("identity", null)
    Log.d("OBTENER TOKEN", ("(" + (token?.takeLast(4)) + ")  " + token))
    return token
}

fun saveAuthToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("QuePasaToken", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("identity", token)
    Log.d("GUARDAR TOKEN", ("(" + (token.takeLast(4)) + ")  " + token))
    editor.apply()
}