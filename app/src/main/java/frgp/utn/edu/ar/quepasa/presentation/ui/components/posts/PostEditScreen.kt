package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.previews.PostEditImagesPreview
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.images.ImageViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.media.PictureViewModel
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
fun PostEditScreen(navController: NavHostController, user: User?, postId: Int) {
    val context = LocalContext.current
    val viewModel: PostViewModel = hiltViewModel()
    val typeViewModel: PostTypeViewModel = hiltViewModel()
    val subtypeViewModel: PostSubtypeViewModel = hiltViewModel()
    val imageViewModel = ImageViewModel()
    val postPictureViewModel: PostPictureViewModel = hiltViewModel()
    val pictureViewModel: PictureViewModel = hiltViewModel()

    BaseComponent(navController, user, "Modificar publicación", true) {
        LaunchedEffect(Unit) {
            viewModel.getPostById(postId)
            viewModel.toggleValidationField(0, true)
            viewModel.toggleValidationField(1, true)
            postPictureViewModel.getPicturesByPost(postId, 0, 10)
            pictureViewModel.setPicturesBitmap(postPictureViewModel.picturesId.value)
        }

        val post by viewModel.post.collectAsState()

        if (post != null) {
            post?.let { safePost ->
                var title by remember { mutableStateOf(safePost.title) }
                var audience by remember { mutableStateOf(safePost.audience.name) }
                var description by remember { mutableStateOf(safePost.description) }
                var neighbourhood by remember { mutableLongStateOf(safePost.neighbourhood!!.id) }
                var type by remember { mutableIntStateOf(safePost.subtype.type.id) }
                var subtype by remember { mutableIntStateOf(safePost.subtype.id) }
                var tag by remember { mutableStateOf("") }
                val tags by viewModel.tags.collectAsState()

                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    androidx.compose.foundation.rememberScrollState().let { scrollState ->
                        androidx.compose.foundation.layout.Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(scrollState)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TypeField(
                                    modifier = Modifier.weight(1f),
                                    typeViewModel,
                                    subtype = subtype,
                                    loadBySubtype = true,
                                    onItemSelected = {
                                        type = it
                                        subtype = subtypeViewModel.getSubtypeByType(type)
                                    }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                TypeSubtypeField(
                                    modifier = Modifier.weight(1f),
                                    viewModel = subtypeViewModel,
                                    type = type,
                                    subtype = subtype,
                                    loadBySelected = true,
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
                                    loadBySelected = true,
                                    onItemSelected = { neighbourhood = it }
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            TagField(
                                modifier = Modifier.padding(2.dp),
                                value = tag,
                                validator = {
                                    StringValidator(title)
                                        .isNotBlank()
                                        .hasMaximumLength(15)
                                        .hasMinimumLength(3)
                                },
                                onChange = { newTags ->
                                    tag = newTags
                                },
                                onValidityChange = { status -> viewModel.toggleTagValidationField(status) },
                                onAdded = { tag = "" },
                                viewModel
                            )

                            if (tags.isNotEmpty()) {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    maxItemsInEachRow = 3
                                ) {
                                    tags.forEachIndexed { index, tag ->
                                        TagValue(
                                            modifier = Modifier
                                                .padding(4.dp),
                                            value = tag,
                                            viewModel = viewModel
                                        )
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
                                        .hasMaximumLength(100)
                                        .hasMinimumLength(1)
                                },
                                onChange = { newTitle -> title = newTitle },
                                onValidityChange = { status -> viewModel.toggleValidationField(0, status) }
                            )
                            DescriptionField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = description,
                                validator = {
                                    StringValidator(title)
                                        .isNotBlank()
                                        .hasMaximumLength(256)
                                        .hasMinimumLength(4)
                                },
                                onChange = { newDesc -> description = newDesc },
                                onValidityChange = { status -> viewModel.toggleValidationField(1, status) }
                            )
                            PostEditImagesPreview(
                                modifier = Modifier,
                                pictureViewModel = pictureViewModel,
                                imageviewModel = imageViewModel,
                                onPictureDelete = { id -> pictureViewModel.flagPictureForDeletion(id) }
                            )
                            ImageField(modifier = Modifier.fillMaxWidth(), viewModel = imageViewModel)
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            CoroutineScope(IO).launch {
                                val validation = viewModel.checkValidationFields()

                                if (validation) {
                                    val result = viewModel.updatePost(
                                        id = postId,
                                        audience = audience,
                                        title = title,
                                        subtype = subtype,
                                        description = description,
                                        neighbourhood = neighbourhood,
                                        tags = tags
                                    )

                                    if(pictureViewModel.picturesForDeletion.value.isNotEmpty()) {
                                        pictureViewModel.picturesForDeletion.value.forEach { picture ->
                                            postPictureViewModel.deletePicture(picture)
                                        }
                                    }

                                    withContext(Dispatchers.Main) {
                                        if (result) {
                                            if (!imageViewModel.areUrisEmpty()) {
                                                viewModel.post.value?.let {
                                                    imageViewModel.selectedUris.value.forEach { uri ->
                                                        postPictureViewModel.upload(context, uri, it.id)
                                                    }
                                                }
                                            }
                                            navController.navigate("home")
                                            Toast.makeText(context, "Publicación modificada", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Publicación no modificada (error)", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Tiene campos sin completar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }) {
                            Text("Modificar")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PostEditScreenPreview() {
    val navController = rememberNavController()
    val user = User(1, "", "", emptySet(), "", null, null, emptySet(), Role.USER, true)
    PostEditScreen(navController = navController, user = user,1)
}