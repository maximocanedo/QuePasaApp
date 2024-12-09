package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.ui.components.basic.GradientDivider
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostDataViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel

@Composable
fun PostEditForm(
    modifier: Modifier,
    formViewModel: PostFormViewModel,
    dataViewModel: PostDataViewModel,
    imgViewModel: ImageViewModel,
    picViewModel: PictureViewModel,
    postPicViewModel: PostPictureViewModel,
    postId: Int
) {
    LaunchedEffect(Unit) {
        dataViewModel.getPostById(postId)
        formViewModel.toggleValidationField(0, true)
        formViewModel.toggleValidationField(1, true)
        postPicViewModel.getPicturesByPost(postId, 0, 10)
        picViewModel.setPicturesBitmap(postPicViewModel.picturesId.value)
    }

    val post by dataViewModel.post.collectAsState()

    post?.let { safePost ->
        formViewModel.populatePostFields(safePost)

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostDropdownSection(viewModel = formViewModel)

            Spacer(modifier = Modifier.height(8.dp))
            
            PostTagSection(viewModel = formViewModel)

            Spacer(modifier = Modifier.height(8.dp))
            GradientDivider(modifier = modifier)
            Spacer(modifier = Modifier.height(8.dp))

            PostTextSection(viewModel = formViewModel)
            
            PostImageSection(picViewModel = picViewModel, imgViewModel = imgViewModel)
        }
    } ?: run {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp)
            )
        }
    }
}