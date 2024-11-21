package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R

@Composable
fun ImageField(
    modifier: Modifier,
    onAdd: (List<Uri>) -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            onAdd(uriList)
        }
    )
    Row(modifier = modifier.padding(horizontal = 24.dp), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = {
            galleryLauncher.launch("image/*")
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
    ImageField(modifier = Modifier, onAdd = {})
}