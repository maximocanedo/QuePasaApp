package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image.ImageField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image.ImagePreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel

@Composable
fun PostImageSection(viewModel: ImageViewModel) {
    val uris = viewModel.selectedUris.collectAsState()

    ImageField(
        modifier = Modifier.fillMaxWidth(),
        onAdd = { viewModel.addImages(it) }
    )

    ImagePreview(
        modifier = Modifier,
        uris = uris.value,
        onClear = { viewModel.clearImage(it) }
    )
}