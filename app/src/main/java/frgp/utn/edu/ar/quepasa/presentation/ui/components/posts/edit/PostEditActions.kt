package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostDataViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PostEditActions(
    modifier: Modifier,
    navController: NavHostController,
    formViewModel: PostFormViewModel,
    dataViewModel: PostDataViewModel,
    picViewModel: PictureViewModel,
    imgViewModel: ImageViewModel
) {
    val context = LocalContext.current
    val postPicViewModel: PostPictureViewModel = hiltViewModel()

    Spacer(modifier = Modifier.height(8.dp))

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
                        onPostEdit(
                            context = context,
                            navController = navController,
                            formViewModel = formViewModel,
                            dataViewModel = dataViewModel,
                            picViewModel = picViewModel,
                            imgViewModel = imgViewModel,
                            postPicViewModel = postPicViewModel
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
            Text("Editar")
        }
    }
}

fun onPostEdit(
    context: Context,
    navController: NavHostController,
    formViewModel: PostFormViewModel,
    dataViewModel: PostDataViewModel,
    picViewModel: PictureViewModel,
    imgViewModel: ImageViewModel,
    postPicViewModel: PostPictureViewModel
) {
    CoroutineScope(IO).launch {
        val result = dataViewModel.updatePost(
            id = formViewModel.getId(),
            audience = formViewModel.getAudience(),
            title = formViewModel.getTitle(),
            subtype = formViewModel.getSubtype(),
            description = formViewModel.getDescription(),
            neighbourhood = formViewModel.getNeighbourhood(),
            tags = formViewModel.getTags()
        )

        withContext(Dispatchers.Main) {
            if (result) {
                if(!picViewModel.arePicturesForDeletionEmpty()) {
                    onImagesDelete(picViewModel, postPicViewModel)
                }
                if(!imgViewModel.areUrisEmpty()) {
                    onImagesUpload(context, dataViewModel, imgViewModel, postPicViewModel)
                }
                navController.navigate("home")
                Toast.makeText(context, "Publicación editada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Publicación no editada (error)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun onImagesDelete(
    picViewModel: PictureViewModel,
    postPicViewModel: PostPictureViewModel
) {
    CoroutineScope(IO).launch {
        picViewModel.picturesForDeletion.value.forEach { picture ->
            postPicViewModel.deletePicture(picture)
        }
    }
}

fun onImagesUpload(
    context: Context,
    dataViewModel: PostDataViewModel,
    imgViewModel: ImageViewModel,
    postPicViewModel: PostPictureViewModel
) {
    CoroutineScope(IO).launch {
        dataViewModel.post.value?.let {
            imgViewModel.selectedUris.value.forEach { uri ->
                postPicViewModel.upload(context, uri, it.id)
            }
        }
    }
}