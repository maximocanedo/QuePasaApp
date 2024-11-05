package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.DescriptionField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TagField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TitleField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TypeField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.TypeSubtypeField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel
import frgp.utn.edu.ar.quepasa.utils.validators.posts.DescriptionValidator
import frgp.utn.edu.ar.quepasa.utils.validators.posts.TitleValidator

@Composable
fun CreatePostScreen(navController: NavHostController, user: User?) {
    val viewModel: PostViewModel = hiltViewModel()
    BaseComponent(navController, user, "Crear publicación") {
        var title by remember { mutableStateOf("Ingresar título") }
        var description by remember { mutableStateOf("Ingresar descripción") }
        var type by remember { mutableIntStateOf(0) }
        var subtype by remember { mutableStateOf("") }
        var tags by remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(32.dp)) {
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
                onValidityChange = {})
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
                onValidityChange = {})

            TypeField(
                modifier = Modifier.fillMaxWidth(),
                onItemSelected = { type = it })
            Spacer(modifier = Modifier.height(20.dp))
            TypeSubtypeField(
                modifier = Modifier.fillMaxWidth(),
                type,
                onItemSelected = { subtype = it })
            Spacer(modifier = Modifier.height(20.dp))
            TagField(
                modifier = Modifier.fillMaxWidth(),
                value = tags,
                onChange = { newTags -> tags = newTags },
                onValidityChange = {},
                viewModel)
        }
    }
}