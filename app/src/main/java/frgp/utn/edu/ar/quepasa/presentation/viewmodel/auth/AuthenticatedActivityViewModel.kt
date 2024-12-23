package frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.domain.repository.AuthRepository
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthenticatedActivityViewModel @Inject constructor(
    private val auth: AuthRepository,
    private val users: UserRepository
) : ViewModel() {

    private val _authenticated = MutableStateFlow<Boolean>(true)
    val authenticated = _authenticated.asStateFlow()

    private val _au = MutableStateFlow<User?>(null)
    val current = _au.asStateFlow()

    suspend fun getCurrentUser(): User? {
        val u = users.getAuthenticatedUser()
        _authenticated.tryEmit(u != null && u.active)
        _au.update { u }
        return u
    }
}