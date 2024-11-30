package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.edit

import BaseComponent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostDataViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel

@Composable
fun PostEdit(
    navController: NavHostController,
    postId: Int
) {
    BaseComponent(
        navController = navController,
        title = "Editar publicación",
        back = true,
        "postEdit"
    ) {
        val scrollState = rememberScrollState()
        val formViewModel = PostFormViewModel()
        val dataViewModel: PostDataViewModel = hiltViewModel()
        val imgViewModel = ImageViewModel()
        val picViewModel: PictureViewModel = hiltViewModel()
        val postPicViewModel: PostPictureViewModel = hiltViewModel()

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostEditForm(
                modifier = Modifier
                    .fillMaxWidth(),
                formViewModel = formViewModel,
                dataViewModel = dataViewModel,
                imgViewModel = imgViewModel,
                picViewModel = picViewModel,
                postPicViewModel = postPicViewModel,
                postId = postId
            )
            PostEditActions(
                modifier = Modifier
                    .fillMaxWidth(),
                navController = navController,
                formViewModel = formViewModel,
                dataViewModel = dataViewModel,
                picViewModel = picViewModel,
                imgViewModel = imgViewModel
            )
        }
    }
}