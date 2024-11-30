package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.dropdown.DropdownAudienceField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.fields.dropdown.DropdownPostField
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.neighbourhood.NeighbourhoodViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostFormViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostSubtypeViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostTypeViewModel
import frgp.utn.edu.ar.quepasa.utils.audience.audienceToSpanish
import frgp.utn.edu.ar.quepasa.utils.audience.audiencesToSpanish

@Composable
fun PostDropdownSection(viewModel: PostFormViewModel) {
    val typeViewModel: PostTypeViewModel = hiltViewModel()
    val subtypeViewModel: PostSubtypeViewModel = hiltViewModel()
    val neighViewModel: NeighbourhoodViewModel = hiltViewModel()

    val user by LocalAuth.current.collectAsState()

    val types = typeViewModel.postTypes.collectAsState()
    val subtypes = subtypeViewModel.postSubtypes.collectAsState()
    val audiences: List<String> = audiencesToSpanish()
    val neighbourhoods = neighViewModel.neighbourhoods.collectAsState()

    var selectedType by remember { mutableIntStateOf(1) }
    var selectedTypeName by remember { mutableStateOf("General") }
    var selectedSubtype by remember { mutableIntStateOf(1) }
    var selectedSubtypeName by remember { mutableStateOf("General") }

    var selectedAudience by remember { mutableStateOf("PUBLIC") }
    var selectedAudienceName by remember { mutableStateOf(audiences.firstOrNull() ?: "Público") }
    var selectedNeighbourhood by remember { mutableLongStateOf(user.user?.neighbourhood?.id ?: 1) }
    viewModel.updateNeighbourhood(selectedNeighbourhood)

    LaunchedEffect(Unit, selectedType) {
        viewModel.updateType(selectedType)
        selectedTypeName = types.value.content.find { it.id == selectedType }?.description ?: "General"
        subtypeViewModel.getSubtypesByType(selectedType, 0, 10)
    }

    LaunchedEffect(Unit, selectedSubtype) {
        viewModel.updateSubtype(selectedSubtype)
        selectedSubtypeName = subtypes.value.content.find { it.id == selectedSubtype }?.description ?: "General"
    }

    LaunchedEffect(subtypes.value.content) {
        if(subtypes.value.content.isNotEmpty()) {
            val firstSubtype = subtypes.value.content.first()
            selectedSubtype = firstSubtype.id
            selectedSubtypeName = firstSubtype.description
        }
    }

    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropdownPostField(
            modifier = Modifier.weight(1f),
            items = types.value.content.map { it.description },
            itemsId = types.value.content.map { it.id },
            selectedItem = selectedTypeName,
            onItemSelected = { id ->
                selectedType = id as Int
            },
            enabled = true,
            label = "Tipo"
        )
        Spacer(modifier = Modifier.width(8.dp))
        DropdownPostField(
            modifier = Modifier.weight(1f),
            items = subtypes.value.content.map { it.description },
            itemsId = subtypes.value.content.map { it.id },
            selectedItem = selectedSubtypeName,
            onItemSelected = { id ->
                selectedSubtype = id as Int
                viewModel.updateSubtype(id.toInt())
            },
            enabled = true,
            label = "Subtipo"
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        DropdownAudienceField(
            modifier = Modifier.weight(1f),
            items = audiences,
            selectedItem = selectedAudienceName,
            onItemSelected = {
                selectedAudience = it
                selectedAudienceName = audienceToSpanish(it)
                viewModel.updateAudience(it)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        DropdownPostField(
            modifier = Modifier.weight(1f),
            items = neighbourhoods.value.content.map { it.name },
            itemsId = neighbourhoods.value.content.map { it.id },
            selectedItem = user.user?.neighbourhood?.name ?: neighbourhoods.value.content.first().name,
            onItemSelected = { id ->
                selectedNeighbourhood = id as Long
                viewModel.updateNeighbourhood(id.toLong())
            },
            enabled = false,
            leadIcon = {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = null
                )
            }
        )
    }
}