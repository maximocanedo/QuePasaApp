package frgp.utn.edu.ar.quepasa

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import frgp.utn.edu.ar.quepasa.presentation.activity.MainNavigationActivity
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity

class MainActivity : AuthenticatedActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainNavigationActivity::class.java)
        startActivity(intent)
        finish()
    }
}