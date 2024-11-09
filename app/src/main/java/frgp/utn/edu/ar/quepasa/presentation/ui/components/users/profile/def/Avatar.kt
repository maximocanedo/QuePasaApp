package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def

import androidx.annotation.ColorInt
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import coil3.compose.AsyncImage
import java.util.Locale
import kotlin.math.absoluteValue


@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

@Composable
fun Avatar(imageUrl: String?, description: String, size: Dp = 40.dp) {
    Surface(
        modifier = Modifier.size(size),
        shape = androidx.compose.foundation.shape.CircleShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainer),
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
            ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = (imageUrl),
                    contentDescription = description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(size)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                )
            } else {
                Text(
                    text = getInitials(description),
                    style = if(size > 80.dp) MaterialTheme.typography.headlineLarge
                    else MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

fun getInitials(name: String): String {
    val names = name.split(" ").map {
        it.replace(",", "")
            .replace(".", "")
            .replace(":", "")
            .replace("_", "")
            .replace("-", "")
            .uppercase(Locale.getDefault())
    }
    val firstName = names.first()
    if(names.size > 1) {
        val lastName = names.last()
        return firstName.first().toString() + lastName.first().toString()
    } else if(firstName.length > 1) return firstName.first().toString() + firstName[1].toString()
    else return firstName.first().toString()
}

@Composable @Preview
fun AvatarPreview() {
    Avatar(
        imageUrl = "https://treenewal.com/wp-content/uploads/2020/11/environmental_factors_affecting_trees.png",
        description = "Andrés Alberto",
        size = 40.dp
    )

}