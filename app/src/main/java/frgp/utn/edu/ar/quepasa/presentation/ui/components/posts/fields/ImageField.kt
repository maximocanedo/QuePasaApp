package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields

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
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.End) {
        IconButton(onClick = {
            // TODO: Not implemented yet
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
    ImageField(modifier = Modifier)
}