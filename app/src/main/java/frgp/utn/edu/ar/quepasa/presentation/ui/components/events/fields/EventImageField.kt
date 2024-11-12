package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel

@Composable
fun EventImageField(
    modifier: Modifier,
    addImages: (List<Uri>) -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            addImages(uriList)
        }
    )
    Row(modifier = modifier, horizontalArrangement = Arrangement.End) {
        IconButton(onClick = {
            galleryLauncher.launch("image/*")
        }) {
            Icon(
                painter = painterResource(R.drawable.baseline_image_24),
                contentDescription = "Image Add",
            )
        }
    }
}

@Preview
@Composable
fun ImageFieldPreview() {
    val imageViewModel = ImageViewModel()
    EventImageField(
        modifier = Modifier,
        imageViewModel::addImages
    )
}