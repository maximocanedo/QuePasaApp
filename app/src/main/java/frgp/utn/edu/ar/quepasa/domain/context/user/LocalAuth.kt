package frgp.utn.edu.ar.quepasa.domain.context.user

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow

val LocalAuth = compositionLocalOf<MutableStateFlow<AuthenticationContext>> { MutableStateFlow(AuthenticationContext(null)) }
val LocalSnack = compositionLocalOf { SnackbarHostState() }