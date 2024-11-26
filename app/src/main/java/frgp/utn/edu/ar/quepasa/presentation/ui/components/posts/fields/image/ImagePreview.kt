package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ImagePreview(
    modifier: Modifier,
    bitmaps: List<Bitmap?>,
    uris: List<Uri>,
    onClearBitmap: (Bitmap) -> Unit,
    onClearUri: (Uri) -> Unit,
    onDeleteBitmap: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val borderColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Start
    ) {
        if(bitmaps.isNotEmpty()) {
            bitmaps.forEachIndexed { index, bitmap ->
                if (bitmap != null) {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .size(75.dp)
                            .border(
                                BorderStroke(1.dp, color = borderColor),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Image Preview",
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = {
                                onClearBitmap(bitmap)
                                onDeleteBitmap(index)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear Image",
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                }
            }
        }

        uris.forEach { uri ->
            if(uri.path != null) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(75.dp)
                        .border(
                            BorderStroke(1.dp, color = borderColor),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = "content://media${uri.path}"),
                        contentDescription = "Image Preview",
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = {
                            onClearUri(uri)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear Image",
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Preview
@Composable
fun ImageListPreview() {
    ImagePreview(
        modifier = Modifier,
        bitmaps = emptyList(),
        uris = emptyList(),
        onClearBitmap = {},
        onClearUri = {},
        onDeleteBitmap = {}
    )
}