package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.edit

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.text.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.text.TitleField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel
import quepasa.api.validators.commons.StringValidator

@Composable
fun PostTextSection(viewModel: PostFormViewModel) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()

    TitleField(
        modifier = Modifier,
        value = title,
        validator = { value ->
            StringValidator(value)
                .isNotBlank()
                .hasMaximumLength(50)
                .hasMinimumLength(4)
        },
        onChange = { value, change -> {}
            viewModel.updateTitle(value)
            if(change) {
                viewModel.toggleValidationField(0, true)
            }
            else {
                viewModel.toggleValidationField(0, false)
            }
        },
        serverError = null,
        clearServerError = {}
    )

    DescriptionField(
        modifier = Modifier.height(200.dp),
        value = description,
        validator = { value ->
            StringValidator(value)
                .isNotBlank()
                .hasMaximumLength(256)
                .hasMinimumLength(1)
        },
        onChange = { value, change ->
            viewModel.updateDescription(value)
            if(change) {
                viewModel.toggleValidationField(1, true)
            }
            else {
                viewModel.toggleValidationField(1, false)
            }
        },
        serverError = null,
        clearServerError = {}
    )
}