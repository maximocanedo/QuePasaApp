package frgp.utn.edu.ar.quepasa.domain.repository

import android.util.Log
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.source.remote.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {

    suspend fun checkUsernameAvailability(username: String): Boolean {
        var x = userService.checkUserExists(username);
        Log.d("Chequeando User @$username", x.code().toString());
        return x.code() == 404; // Si responde con 404, está disponible.
    }

    suspend fun getAuthenticatedUser(): User? = userService.getAuthenticatedUser().body()

}