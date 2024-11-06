package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.AudienceField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.NeighbourhoodField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TagField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TagValue
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TitleField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TypeField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TypeSubtypeField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import frgp.utn.edu.ar.quepasa.utils.validators.posts.DescriptionValidator
import frgp.utn.edu.ar.quepasa.utils.validators.posts.TitleValidator

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePostScreen(navController: NavHostController, user: User?) {
    val viewModel: PostViewModel = hiltViewModel()
    BaseComponent(navController, user, "Crear publicación") {
        var title by remember { mutableStateOf("Ingresar título") }
        var audience by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("Ingresar descripción") }
        var neighbourhood by remember { mutableLongStateOf(0) }
        var type by remember { mutableIntStateOf(1) }
        var subtype by remember { mutableStateOf("") }
        var tag by remember { mutableStateOf("") }
        val tags by viewModel.tags.collectAsState()

        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TypeField(
                    modifier = Modifier.weight(1f),
                    onItemSelected = {
                        type = it
                        subtype = ""
                    })
                TypeSubtypeField(
                    modifier = Modifier.weight(1f),
                    type,
                    onItemSelected = { subtype = it }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                AudienceField(
                    modifier = Modifier.weight(1f),
                    onItemSelected = { audience = it }
                )
                NeighbourhoodField(
                    modifier = Modifier.weight(1f),
                    audience = audience,
                    onItemSelected = { neighbourhood = it }
                    )
            }

            Spacer(modifier = Modifier.height(20.dp))
            TagField(
                modifier = Modifier.fillMaxWidth(),
                value = tag,
                onChange = {
                        newTags -> tag = newTags
                },
                onValidityChange = {},
                viewModel
            )

            if(tags.isNotEmpty()) {
                FlowRow(modifier = Modifier.fillMaxWidth()) {
                    tags.forEachIndexed { index, tag ->
                        TagValue(
                            modifier = Modifier
                                .weight(1f),
                            value = tag,
                            viewModel = viewModel
                        )

                        if(index < tags.size - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            TitleField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title,
                validator = {
                    TitleValidator(title)
                        .notEmpty()
                        .meetsMinimumLength()
                        .meetsMaximumLength()
                },
                onChange = { newTitle -> title = newTitle },
                onValidityChange = {}
            )
            DescriptionField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = description,
                validator = {
                    DescriptionValidator(description)
                        .notEmpty()
                        .meetsMinimumLength()
                        .meetsMaximumLength()
                },
                onChange = { newDesc -> description = newDesc },
                onValidityChange = {}
            )

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {

                }) {
                    Text("Publicar")
                }
            }
        }
    }
}
