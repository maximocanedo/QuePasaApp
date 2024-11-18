package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.presentation.ui.components.LoadMoreButton
import kotlinx.coroutines.delay

@Composable
fun NeighbourhoodList(
    items: List<Neighbourhood>,
    modifier: Modifier = Modifier,
    selectable: Boolean = false,
    selected: List<Neighbourhood> = emptyList(),
    onClick: (Neighbourhood) -> Unit = {  },
    onCheckedChange: (Neighbourhood, Boolean) -> Unit = { neighbourhood, checked ->  },
    loadable: Boolean = true,
    onNextRequest: suspend () -> Unit = { },
    isLoading: Boolean = false,
    showGeographicalContext: Boolean = true,
    maxSelected: Int? = null,
    allowSelectingDisabledEntries: Boolean = false
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(items) { neighbourhood ->
            NeighbourhoodListItem(
                data = neighbourhood,
                modifier = Modifier,
                onClick = onClick,
                showGeographicalContext = showGeographicalContext,
                selectable = selectable, // && ( neighbourhood.active || allowSelectingDisabledEntries ),
                selected = selected.any { it.id == neighbourhood.id },
                onSelect = { item, checked ->
                    if(!neighbourhood.active && !allowSelectingDisabledEntries) {
                        Toast.makeText(
                            context,
                            "Este barrio está deshabilitado. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if(checked && maxSelected != null && selected.size == maxSelected) {
                        Toast.makeText(
                            context,
                            if(maxSelected == 1)
                                "Sólo podés seleccionar un barrio. "
                            else
                                "No podés seleccionar más de $maxSelected barrios. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else onCheckedChange(item, checked)
                },
                enabled = (
                        maxSelected != null
                        && selected.size < maxSelected
                    ) || selected.any { it.id == neighbourhood.id }
            )
        }
        item {
            LoadMoreButton(
                modifier = Modifier.fillMaxWidth(),
                loadable = loadable,
                loading = isLoading,
                onLoadRequest = onNextRequest
            )
        }
    }

}

@Composable
@Preview
fun NeighbourhoodListPreview() {
    var showing by remember { mutableStateOf(5) }
    val items = remember { mutableStateListOf<Neighbourhood>() }
    var selectable by remember { mutableStateOf(true) }
    var showGeographicalContext by remember { mutableStateOf(false) }
    val modifier = Modifier.fillMaxWidth()
    var loading by remember { mutableStateOf(false) }
    var selectedItems = remember { mutableStateListOf<Neighbourhood>() }

    LaunchedEffect(showing) {
        val newItems = NeighbourhoodsMDFP.take(showing).toList()
        items.clear()
        items.addAll(newItems)
    }

    Column(modifier.wrapContentHeight()) {
        ListItem(
            trailingContent = {
                Switch(
                    checked = selectable,
                    onCheckedChange = { selectable = it }
                )
            },
            headlineContent = { Text("Habilitar selección de elementos") }
        )
        ListItem(
            trailingContent = {
                Switch(
                    checked = showGeographicalContext,
                    onCheckedChange = { showGeographicalContext = it }
                )
            },
            headlineContent = { Text("Mostrar contexto geográfico") }
        )
        Row(Modifier.padding(12.dp)) {
            selectedItems.forEach {
                InputChip(
                    selected = false,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    onClick = {

                    },
                    label = { Text(it.name) }
                )
            }
        }
        HorizontalDivider()
        Row(modifier.wrapContentHeight()) {
            NeighbourhoodList(
                items = items,
                selectable = selectable,
                showGeographicalContext = showGeographicalContext,
                selected = selectedItems,
                maxSelected = 1,
                loadable = true,
                isLoading = loading,
                onNextRequest = {
                    if (!loading) {
                        loading = true
                        delay(3000)
                        showing += 5
                        loading = false
                    }
                },
                onCheckedChange = { neighbourhood, isChecked ->
                    if (isChecked) {
                        selectedItems.add(neighbourhood)
                    } else {
                        selectedItems.remove(neighbourhood)
                    }
                }
            )
        }
    }
}
