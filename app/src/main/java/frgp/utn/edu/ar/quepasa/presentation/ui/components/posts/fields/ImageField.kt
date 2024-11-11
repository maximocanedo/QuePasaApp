package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
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

@Composable
fun ImageField(
    modifier: Modifier,
    pickMultipleMedia: ActivityResultLauncher<PickVisualMediaRequest>?
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.End) {
        IconButton(onClick = {
            pickMultipleMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            } ) {
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
    val pickMultipleMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null
    ImageField(modifier = Modifier, pickMultipleMedia)
}