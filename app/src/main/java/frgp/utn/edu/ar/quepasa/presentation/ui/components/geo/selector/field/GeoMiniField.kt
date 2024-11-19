package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.NeighbourhoodChipContainer
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list.NeighbourhoodsMDFP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoMiniField(
    modifier: Modifier = Modifier,
    value: Set<Neighbourhood>,
    onEditRequest: () -> Unit,
    onUnselect: (Neighbourhood) -> Unit,
    valid: Boolean = true,
    label: String = "Barrio",
    feedback: String? = null
) {
    val defColor = MaterialTheme.colorScheme.onSurfaceVariant
    val errColor = MaterialTheme.colorScheme.error
    val styleSelected = true
    val contentColor by remember { derivedStateOf { if (feedback != null) errColor else defColor } }
    Column(modifier = Modifier.width(OutlinedTextFieldDefaults.MinWidth).then(modifier), verticalArrangement = Center) {
        Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor
                )
                if(value.isNotEmpty()) NeighbourhoodChipContainer(
                    modifier = Modifier.padding(start = 0.dp),
                    data = value,
                    onUnselectRequest = onUnselect
                )
                else LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if(value.isEmpty()) item() {
                        InputChip(
                            selected = false,
                            onClick = { onEditRequest() },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = "Seleccionar"
                                )
                            },
                            label = { Text("Seleccionar") }
                        )
                    }

                    /**
                     * item(value.first().id) {
                     *                         InputChip(
                     *                             selected = styleSelected,
                     *                             onClick = { onUnselect(value.first()) },
                     *                             label = { Text(value.first().name) }
                     *                         )
                     *                     }
                     *                     if(value.size > 1) {
                     *                         item(value.elementAt(1).id) {
                     *                             InputChip(
                     *                                 selected = styleSelected,
                     *                                 onClick = { /*TODO*/ },
                     *                                 label = { Text("+${value.size}") }
                     *                             )
                     *                         }
                     *                     }
                     */
                }
            }
            Column(
                modifier = Modifier.height(64.dp),
                verticalArrangement = Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = contentColor
                    ),
                    onClick = onEditRequest
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Editar"
                    )
                }
            }
        }
        if(feedback != null) Row(modifier = Modifier.padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)) {
            Text(
                text = feedback,
                color = contentColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable @Preview
fun GeoMiniFieldPreview() {
    GeoMiniField(
        value = NeighbourhoodsMDFP.take(3).toSet(),
        onEditRequest = {  },
        onUnselect = {  }
    )
}

@Composable @Preview
fun GeoMiniFieldErrorPreview() {
    GeoMiniField(
        value = NeighbourhoodsMDFP.take(2).toSet(),
        onEditRequest = {  },
        feedback = "El barrio seleccionado está inhabilitado",
        onUnselect = {  }
    )
}

@Composable @Preview
fun GeoMiniFieldEmptyPreview() {
    GeoMiniField(
        value = setOf<Neighbourhood>(),
        onEditRequest = {  },
        feedback = null,
        onUnselect = {  }
    )
}