package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth.AuthenticatedActivityViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class AuthenticatedActivity : ComponentActivity() {

    private val authViewModel: AuthenticatedActivityViewModel by viewModels()

    val _authenticatedUser = MutableStateFlow<User?>(null)
    val authenticatedUser = _authenticatedUser.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val x = authViewModel.getCurrentUser()
            _authenticatedUser.update { x }
            authViewModel.authenticated.collect { isAuthenticated ->
                if (!isAuthenticated) {
                    val intent = Intent(this@AuthenticatedActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    onAuthenticated()
                }
            }
        }
    }

    open fun onAuthenticated() {

    }

    suspend fun getCurrentUser() : User? {
        return authViewModel.getCurrentUser()
    }

    suspend fun getCurrentUserOrDie() : User {
        return getCurrentUser() ?: throw Exception("No user authenticated.")
    }

}