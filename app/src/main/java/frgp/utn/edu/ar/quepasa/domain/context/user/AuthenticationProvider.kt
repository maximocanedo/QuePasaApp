package frgp.utn.edu.ar.quepasa.domain.context.user

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import frgp.utn.edu.ar.quepasa.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AuthenticationProvider(initialValue: User? = null, content: @Composable () -> Unit) {
    val initial = MutableStateFlow(initialValue.toContext())
    CompositionLocalProvider(LocalAuth provides initial) {
        content()
    }
}

@Composable
fun SnackProvider(initialValue: SnackbarHostState? = null, content: @Composable () -> Unit) {
    val initial = remember { initialValue ?: SnackbarHostState() }
    CompositionLocalProvider(LocalSnack provides initial) {
        content()
    }
}