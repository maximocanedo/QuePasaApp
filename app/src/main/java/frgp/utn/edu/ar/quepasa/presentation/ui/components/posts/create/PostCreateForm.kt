package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.text.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.text.TitleField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel
import quepasa.api.validators.commons.StringValidator

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

        PostTextSection(viewModel = viewModel)
        
        PostImageSection(viewModel = imgViewModel)
    }
}