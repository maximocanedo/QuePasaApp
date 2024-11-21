package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.dto.request.CodeVerificationRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.UserPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.model.auth.TotpDetails
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @HEAD("users/{username}")
    suspend fun checkUserExists(@Path("username") username: String): Response<Void>

    @GET("users/{username}")
    suspend fun findByUsername(@Path("username") username: String): Response<User>

    @PATCH("users/{username}")
    suspend fun editByUsername(@Path("username") username: String, @Body request: UserPatchEditRequest): Response<User>

    @DELETE("users/{username}")
    suspend fun deleteByUsername(@Path("username") username: String): Response<Void>

    @POST("users/me/mail")
    suspend fun requestMailVerificationCode(@Body mail: String): Response<Mail>

    @POST("users/me/mail/verify")
    suspend fun verifyMail(@Body request: CodeVerificationRequest): Response<Mail>

    @DELETE("users/me/mail")
    suspend fun deleteMail(@Query("subject") subject: String): Response<Void>

    @POST("users/me/phone")
    suspend fun requestPhoneVerificationCode(@Body phone: String): Response<Phone>

    @POST("users/me/phone/verify")
    suspend fun verifyPhone(@Body request: CodeVerificationRequest): Response<Phone>

    @DELETE("users/me/phone")
    suspend fun deletePhone(@Query("subject") subject: String): Response<Void>

    @GET("users/me")
    suspend fun getAuthenticatedUser(): Response<User>

    @GET("users/me/totp")
    suspend fun getTotpDetails(): Response<TotpDetails>

    @POST("users/me/totp")
    suspend fun enableTotp(): Response<TotpDetails>

    @DELETE("users/me/totp")
    suspend fun disableTotp(): Response<Void>

    @PATCH("users/me")
    suspend fun editAuthenticatedUser(@Body request: UserPatchEditRequest): Response<User>

    @DELETE("users/me")
    suspend fun deleteAuthenticatedUser(): Response<Void>

    @POST("users/me/password")
    suspend fun updateMyPassword(@Body newPassword: RequestBody): Response<Void>




}