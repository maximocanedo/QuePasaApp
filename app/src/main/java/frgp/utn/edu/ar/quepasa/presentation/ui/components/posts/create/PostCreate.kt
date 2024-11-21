package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

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
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostDataViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel

@Composable
fun PostCreate(navController: NavHostController) {
    BaseComponent(
        navController = navController,
        title = "Crear publicación",
        back = true
    ) {
        val scrollState = rememberScrollState()
        val formViewModel = PostFormViewModel()
        val dataViewModel: PostDataViewModel = hiltViewModel()
        val imgViewModel = ImageViewModel()

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostCreateForm(
                modifier = Modifier
                    .fillMaxWidth(),
                viewModel = formViewModel,
                imgViewModel = imgViewModel
            )
            PostCreateActions(
                modifier = Modifier
                    .fillMaxWidth(),
                navController = navController,
                formViewModel = formViewModel,
                dataViewModel = dataViewModel,
                imgViewModel = imgViewModel
            )
        }
    }
}