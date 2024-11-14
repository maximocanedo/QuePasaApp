package frgp.utn.edu.ar.quepasa.domain.context.feedback

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FeedbackProvider(initialValue: Feedback? = null, content: @Composable () -> Unit) {
    val initial = MutableStateFlow(initialValue)
    CompositionLocalProvider(LocalFeedback provides initial) {
        content()
    }
}