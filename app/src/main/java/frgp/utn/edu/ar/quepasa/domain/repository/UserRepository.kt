package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.request.CodeVerificationRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.UserPatchEditRequest
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.source.remote.UserService
import kotlinx.coroutines.runBlocking
import quepasa.api.verifiers.UserVerifier
import javax.inject.Inject

open class UserRepository @Inject constructor(
    private val userService: UserService
): UserVerifier {

    private val handler: ApiResponseHandler = ApiResponseHandler()

    override fun existsByUsername(username: String): Boolean = runBlocking {
        val x = userService.checkUserExists(username)
        val y = x.code()
        return@runBlocking y != 404
    }

    suspend fun getAuthenticatedUser(): User? = userService.getAuthenticatedUser().body()

    suspend fun findByUsername(username: String): ApiResponse<User?> {
        return handler.getResponse(userService.findByUsername(username))
    }

    suspend fun editByUsername(username: String, request: UserPatchEditRequest): ApiResponse<User?> {
        return handler.getResponse(userService.editByUsername(username, request))
    }

    suspend fun deleteByUsername(username: String): ApiResponse<Void?> {
        return handler.getResponse(userService.deleteByUsername(username))
    }

    suspend fun requestMailVerificationCode(mail: String): ApiResponse<Mail?> {
        return handler.getResponse(userService.requestMailVerificationCode(mail))
    }

    suspend fun verifyMail(request: CodeVerificationRequest): ApiResponse<Mail?> =
        handler.getResponse(userService.verifyMail(request))

    suspend fun requestPhoneVerificationCode(phone: String): ApiResponse<Phone?> =
        handler.getResponse(userService.requestPhoneVerificationCode(phone))

    suspend fun verifyPhone(request: CodeVerificationRequest): ApiResponse<Phone?> =
        handler.getResponse(userService.verifyPhone(request))

    suspend fun editAuthenticatedUser(request: UserPatchEditRequest): ApiResponse<User?> =
        handler.getResponse(userService.editAuthenticatedUser(request))

    suspend fun deleteAuthenticatedUser(): ApiResponse<Void?> =
        handler.getResponse(userService.deleteAuthenticatedUser())

}