package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.dto.request.CodeVerificationRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.UserPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

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

    @POST("users/me/phone")
    suspend fun requestPhoneVerificationCode(@Body phone: String): Response<Phone>

    @POST("users/me/phone/verify")
    suspend fun verifyPhone(@Body request: CodeVerificationRequest): Response<Phone>

    @GET("users/me")
    suspend fun getAuthenticatedUser(): Response<User>

    /**
     * Solucionar endpoints de TOTP.
     */

    @PATCH("users/me")
    suspend fun editAuthenticatedUser(@Body request: UserPatchEditRequest): Response<User>

    @DELETE("users/me")
    suspend fun deleteAuthenticatedUser(): Response<Void>




}