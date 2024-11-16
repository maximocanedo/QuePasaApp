package frgp.utn.edu.ar.quepasa.presentation.ui.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import frgp.utn.edu.ar.quepasa.presentation.ui.components.images.dialog.ImageFullScreenDialog

@Composable
fun ImagesListPreview(bitmaps: List<Bitmap?>) {
    val bitmapsToDisplay = bitmaps.take(5)
    val expandedImageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(bitmapsToDisplay) { bitmap ->
                if (bitmap != null) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                expandedImageBitmap.value = bitmap
                            }
                    ) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Image preview",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    expandedImageBitmap.value?.let { bitmap ->
        ImageFullScreenDialog(bitmap = bitmap, onDismiss = { expandedImageBitmap.value = null })
    }
}

@Preview
@Composable
fun PreviewImagesListPreview() {
    ImagesListPreview(bitmaps = emptyList())
}