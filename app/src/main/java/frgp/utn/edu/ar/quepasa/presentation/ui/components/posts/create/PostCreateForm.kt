package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
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
        Box(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
        )
        Spacer(modifier = Modifier.height(8.dp))

        PostTextSection(viewModel = viewModel)
        
        PostImageSection(viewModel = imgViewModel)
    }
}