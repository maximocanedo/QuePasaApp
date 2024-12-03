package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.tags.TagField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.tags.TagValue
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel
import quepasa.api.validators.commons.StringValidator

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostTagSection(viewModel: PostFormViewModel) {
    val tag by viewModel.tag.collectAsState()
    val tags by viewModel.tags.collectAsState()
    val tagCount by viewModel.tagCount.collectAsState()

    TagField(
        value = tag,
        validator = { value ->
            StringValidator(value)
                .isNotBlank()
                .hasMaximumLength(15)
                .hasMinimumLength(3)
        },
        onChange = { value, valid -> if(valid) viewModel.updateTag(value) },
        onAdd = {
            viewModel.addTag(it)
            viewModel.updateTag("")
        }
    )

    if(tags.isNotEmpty()) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            tags.forEachIndexed { index, tag ->
                TagValue(
                    modifier = Modifier,
                    value = tag,
                    onRemove = { viewModel.removeTag(it) }
                )
                if(index < tags.size - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }

    val text: String = if(tagCount != 1) "$tagCount etiquetas añadidas" else "$tagCount etiqueta añadida"
    Text(modifier = Modifier.padding(4.dp), text = text)
}