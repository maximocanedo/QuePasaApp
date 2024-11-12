package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.previews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel

@Composable
fun ImagesPreview(modifier: Modifier, viewModel: ImageViewModel) {
    val uris = viewModel.selectedUris.collectAsState()

    Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
        uris.value.forEach { uri ->
            if(uri.path != null) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .padding(2.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = "content://media${uri.path}"),
                        contentDescription = "Image Preview",
                        modifier = Modifier.matchParentSize()
                    )

                    IconButton(
                        onClick = {
                            viewModel.clearImage(uri)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear Image",
                        )
                    }
                }
            }
        }
    }
}