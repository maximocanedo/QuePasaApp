package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.ui.components.basic.GradientDivider
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel

@Composable
fun PostCreateForm(
    modifier: Modifier,
    viewModel: PostFormViewModel,
    imgViewModel: ImageViewModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostDropdownSection(viewModel = viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        PostTagSection(viewModel = viewModel)

        Spacer(modifier = Modifier.height(8.dp))
        GradientDivider(modifier = modifier)
        Spacer(modifier = Modifier.height(8.dp))

        PostTextSection(viewModel = viewModel)
        
        PostImageSection(viewModel = imgViewModel)
    }
}