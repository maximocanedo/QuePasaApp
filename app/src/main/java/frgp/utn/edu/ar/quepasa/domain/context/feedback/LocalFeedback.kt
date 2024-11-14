package frgp.utn.edu.ar.quepasa.domain.context.feedback

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow

var LocalFeedback = compositionLocalOf<MutableStateFlow<Feedback?>> { MutableStateFlow(null) }