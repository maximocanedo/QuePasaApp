package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image.ImageField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image.ImagePreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel

@Composable
fun PostImageSection(viewModel: ImageViewModel) {
    val uris = viewModel.selectedUris.collectAsState()
    val uriCount by viewModel.uriCount.collectAsState()

    ImageField(
        modifier = Modifier.fillMaxWidth(),
        onAdd = { viewModel.addImages(it) }
    )

    ImagePreview(
        modifier = Modifier,
        bitmaps = emptyList(),
        uris = uris.value,
        onClearBitmap = {},
        onClearUri = { viewModel.clearImage(it) },
        onDeleteBitmap = {}
    )

    val text: String = if(uriCount != 1) "$uriCount imágenes seleccionadas" else "$uriCount imagen seleccionada"
    Text(modifier = Modifier.padding(4.dp), text = text)
}