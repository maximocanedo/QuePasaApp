package frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthenticatedActivityViewModel @Inject constructor(
    private val auth: AuthRepository,
    private val users: UserRepository
) : ViewModel() {

    private val _authenticated = MutableLiveData<Boolean>()
    val authenticated: LiveData<Boolean> = _authenticated

    suspend fun isAuthenticated(): Boolean = users.getAuthenticatedUser() != null

    suspend fun getCurrentUser(): User? = users.getAuthenticatedUser()
}