package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.AudienceField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.ImageField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.NeighbourhoodField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TagField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TagValue
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TitleField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TypeField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TypeSubtypeField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.previews.PostCreateImagesPreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PostPictureViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostSubtypeViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostTypeViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import quepasa.api.validators.commons.StringValidator

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostCreateScreen(navController: NavHostController, user: User?) {
    val context = LocalContext.current
    val viewModel: PostViewModel = hiltViewModel()
    val typeViewModel: PostTypeViewModel = hiltViewModel()
    val subtypeViewModel: PostSubtypeViewModel = hiltViewModel()
    val imageViewModel = ImageViewModel()
    val pictureViewModel: PostPictureViewModel = hiltViewModel()

    BaseComponent(navController, user, "Crear publicación", true) {
        var title by remember { mutableStateOf("") }
        var audience by remember { mutableStateOf("PUBLIC") }
        var description by remember { mutableStateOf("") }
        var neighbourhood by remember { mutableLongStateOf(user?.neighbourhood?.id ?: 1) }
        var type by remember { mutableIntStateOf(1) }
        var subtype by remember { mutableIntStateOf(1) }
        var tag by remember { mutableStateOf("") }
        val tags by viewModel.tags.collectAsState()

        Column(modifier = Modifier.padding(16.dp)) {
            androidx.compose.foundation.rememberScrollState().let { scrollState ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    TypeField(
                        modifier = Modifier.weight(1f),
                        typeViewModel,
                        subtype = subtype,
                        loadBySubtype = false,
                        onItemSelected = {
                            type = it
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    TypeSubtypeField(
                        modifier = Modifier.weight(1f),
                        viewModel = subtypeViewModel,
                        type = type,
                        subtype = subtype,
                        loadBySelected = false,
                        onItemSelected = { subtype = it }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    AudienceField(
                        modifier = Modifier.weight(1f),
                        audience = audience,
                        onItemSelected = { audience = it }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    NeighbourhoodField(
                        modifier = Modifier.weight(1f),
                        audience = audience,
                        neighbourhood = neighbourhood,
                        loadBySelected = false,
                        onItemSelected = { neighbourhood = it }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                TagField(
                    modifier = Modifier.padding(2.dp),
                    value = tag,
                    validator = {
                        StringValidator(tag)
                            .isNotBlank()
                            .hasMaximumLength(15)
                            .hasMinimumLength(3)
                    },
                    onChange = { newTags -> tag = newTags },
                    onValidityChange = { status -> viewModel.toggleTagValidationField(status) },
                    onAdded = { tag = "" },
                    viewModel = viewModel
                )

                if (tags.isNotEmpty()) {
                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        tags.forEachIndexed { index, tag ->
                            TagValue(
                                modifier = Modifier
                                    .weight(1f),
                                value = tag,
                                viewModel = viewModel
                            )

                            if (index < tags.size - 1) {
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                TitleField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = title,
                    validator = {
                        StringValidator(title)
                            .isNotBlank()
                            .hasMaximumLength(30)
                            .hasMinimumLength(4)
                    },
                    onChange = { newTitle -> title = newTitle },
                    onValidityChange = { status -> viewModel.toggleValidationField(0, status) }
                )
                DescriptionField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = description,
                    validator = {
                        StringValidator(description)
                            .isNotBlank()
                            .hasMaximumLength(256)
                            .hasMinimumLength(4)
                    },
                    onChange = { newDesc -> description = newDesc },
                    onValidityChange = { status -> viewModel.toggleValidationField(1, status) }
                )
                PostCreateImagesPreview(modifier = Modifier, viewModel = imageViewModel)
                ImageField(modifier = Modifier.fillMaxWidth(), viewModel = imageViewModel)
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    CoroutineScope(IO).launch {
                        val validation = viewModel.checkValidationFields()

                        if (validation) {
                            val result = viewModel.createPost(
                                audience = audience,
                                title = title,
                                subtype = subtype,
                                description = description,
                                neighbourhood = neighbourhood,
                                tags = tags
                            )

                            withContext(Dispatchers.Main) {
                                if (result) {
                                    if(!imageViewModel.areUrisEmpty()) {
                                        viewModel.post.value?.let {
                                            imageViewModel.selectedUris.value.forEach { uri ->
                                                pictureViewModel.upload(context, uri, it.id)
                                            }
                                        }
                                    }
                                    navController.navigate("home")
                                    Toast.makeText(context, "Publicación creada", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    Toast.makeText(context, "Publicación no creada (error)", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Tiene campos sin completar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }) {
                    Text("Publicar")
                }
            }
        }
    }
}

@Preview
@Composable
fun PostCreateScreenPreview() {
    val navController = rememberNavController()
    val user = User(1, "", "", emptySet(), "", null, null, emptySet(), Role.USER, true)
    PostCreateScreen(navController = navController, user = user)
}