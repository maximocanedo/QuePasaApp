package frgp.utn.edu.ar.quepasa.domain.repository

import android.util.Log
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.source.remote.UserService
import kotlinx.coroutines.runBlocking
import quepasa.api.verifiers.UserVerifier
import javax.inject.Inject

open class UserRepository @Inject constructor(
    private val userService: UserService
): UserVerifier {

    override fun existsByUsername(username: String): Boolean = runBlocking {
        userService.checkUserExists(username).code() != 404
    }

    suspend fun getAuthenticatedUser(): User? = userService.getAuthenticatedUser().body()


}