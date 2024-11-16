package frgp.utn.edu.ar.quepasa.presentation.ui.components.images.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil3.Bitmap

@Composable
fun ImageFullScreenDialog(bitmap: Bitmap, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Imagen", style = MaterialTheme.typography.titleMedium)
        },
        text = {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Full Size Image",
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}