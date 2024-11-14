package frgp.utn.edu.ar.quepasa.domain.context.user

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow

val LocalAuth = compositionLocalOf<MutableStateFlow<AuthenticationContext>> { MutableStateFlow(AuthenticationContext(null)) }