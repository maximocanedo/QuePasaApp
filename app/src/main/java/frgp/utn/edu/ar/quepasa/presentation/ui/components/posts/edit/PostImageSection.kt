package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image.ImageField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image.ImagePreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel

@Composable
fun PostImageSection(picViewModel: PictureViewModel, imgViewModel: ImageViewModel) {
    val bitmaps = picViewModel.pictures.collectAsState()
    val uris = imgViewModel.selectedUris.collectAsState()

    ImageField(
        modifier = Modifier.fillMaxWidth(),
        onAdd = { imgViewModel.addImages(it) }
    )

    ImagePreview(
        modifier = Modifier,
        bitmaps = bitmaps.value,
        uris = uris.value,
        onClearBitmap = { picViewModel.clearBitmap(it) },
        onClearUri = { imgViewModel.clearImage(it) },
        onDeleteBitmap = { picViewModel.flagPictureForDeletion(picViewModel.picturesId.value[it]) }
    )
}