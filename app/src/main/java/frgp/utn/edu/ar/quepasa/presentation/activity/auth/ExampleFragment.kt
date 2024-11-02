package frgp.utn.edu.ar.quepasa.presentation.activity.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.UsernameField
import quepasa.api.validators.users.UsernameValidator
import javax.inject.Inject

@AndroidEntryPoint
class ExampleFragment @Inject constructor(
    private val userRepository: UserRepository
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }
}