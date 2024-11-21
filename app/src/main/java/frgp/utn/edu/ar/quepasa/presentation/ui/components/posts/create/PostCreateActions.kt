package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostDataViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PostCreateActions(
    modifier: Modifier,
    navController: NavHostController,
    formViewModel: PostFormViewModel,
    dataViewModel: PostDataViewModel,
    imgViewModel: ImageViewModel
) {
    val context = LocalContext.current
    val pictureViewModel: PostPictureViewModel = hiltViewModel()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier,
            onClick = {
                val validation = formViewModel.checkValidationFields()

                CoroutineScope(IO).launch {
                    if(validation) {
                        onPostCreate(
                            context = context,
                            navController = navController,
                            formViewModel = formViewModel,
                            dataViewModel = dataViewModel,
                            imgViewModel = imgViewModel,
                            picViewModel = pictureViewModel
                        )
                    }
                    else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Tiene campos sin completar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        ) {
            Text("Publicar")
        }
    }
}

fun onPostCreate(
    context: Context,
    navController: NavHostController,
    formViewModel: PostFormViewModel,
    dataViewModel: PostDataViewModel,
    imgViewModel: ImageViewModel,
    picViewModel: PostPictureViewModel
) {
    CoroutineScope(IO).launch {
        val result = dataViewModel.createPost(
            audience = formViewModel.getAudience(),
            title = formViewModel.getTitle(),
            subtype = formViewModel.getSubtype(),
            description = formViewModel.getDescription(),
            neighbourhood = formViewModel.getNeighbourhood(),
            tags = formViewModel.getTags()
        )

        withContext(Dispatchers.Main) {
            if (result) {
                if (!imgViewModel.areUrisEmpty()) {
                    onImagesUpload(context, dataViewModel, imgViewModel, picViewModel)
                }
                navController.navigate("home")
                Toast.makeText(context, "Publicación creada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Publicación no creada (error)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun onImagesUpload(
    context: Context,
    dataViewModel: PostDataViewModel,
    imgViewModel: ImageViewModel,
    picViewModel: PostPictureViewModel
) {
    CoroutineScope(IO).launch {
        dataViewModel.post.value?.let {
            imgViewModel.selectedUris.value.forEach { uri ->
                picViewModel.upload(context, uri, it.id)
            }
        }
    }
}
