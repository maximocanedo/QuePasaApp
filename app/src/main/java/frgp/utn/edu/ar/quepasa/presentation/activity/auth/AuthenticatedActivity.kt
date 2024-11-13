package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth.AuthenticatedActivityViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class AuthenticatedActivity : ComponentActivity() {

    private val authViewModel: AuthenticatedActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            authViewModel.getCurrentUser()
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