package frgp.utn.edu.ar.quepasa.presentation.ui.components.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ReadMoreText(text: String, modifier: Modifier, style: TextStyle, minLines: Int, maxLines: Int) {
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val displayLines = if (expandedState) maxLines else minLines

    Column(modifier = modifier) {
        Text(
            text = text,
            style = style,
            overflow = TextOverflow.Ellipsis,
            maxLines = displayLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (textLayoutResult.lineCount > minLines - 1) {
                    if (textLayoutResult.isLineEllipsized(minLines - 1)) showReadMoreButtonState =
                        true
                }
            }
        )
        if (showReadMoreButtonState) {
            Text(
                text = if (expandedState) "Leer menos" else "Leer más",
                color = Color.Gray,
                modifier = Modifier.clickable {
                    expandedState = !expandedState
                },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}