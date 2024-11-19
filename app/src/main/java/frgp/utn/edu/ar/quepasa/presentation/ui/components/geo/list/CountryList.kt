package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.InputChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.presentation.ui.components.LoadMoreButton
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryList(
    items: List<Country>,
    modifier: Modifier = Modifier,
    selectable: Boolean = false,
    selected: List<Country> = emptyList(),
    onClick: (Country) -> Unit = {  },
    onCheckedChange: (Country, Boolean) -> Unit = { country, checked ->  },
    loadable: Boolean = true,
    onNextRequest: suspend () -> Unit = { },
    isLoading: Boolean = false,
    maxSelected: Int? = null,
    allowSelectingDisabledEntries: Boolean = false
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(items) { state ->
            CountryListItem(
                data = state,
                modifier = Modifier,
                onClick = onClick,
                selectable = selectable,
                selected = selected.any { it.iso3 == state.iso3 },
                onSelect = { item, checked ->
                    if(!item.active && !allowSelectingDisabledEntries) {
                        Toast.makeText(
                            context,
                            "Este país está deshabilitado. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if(checked && maxSelected != null && selected.size == maxSelected) {
                        Toast.makeText(
                            context,
                            if(maxSelected == 1)
                                "Sólo podés seleccionar un país. "
                            else
                                "No podés seleccionar más de $maxSelected países. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else onCheckedChange(item, checked)
                },
                enabled = (
                        maxSelected != null
                        && selected.size < maxSelected
                    ) || selected.any { it.iso3 == state.iso3 }
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
fun CountryListPreview() {
    var showing by remember { mutableStateOf(5) }
    val items = remember { mutableStateListOf<Country>() }
    var selectable by remember { mutableStateOf(true) }
    val modifier = Modifier.fillMaxWidth()
    var loading by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Country>() }

    LaunchedEffect(showing) {
        val newItems = listOf(ARGENTINA)
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
        Row(Modifier.padding(12.dp)) {
            selectedItems.forEach {
                InputChip(
                    selected = false,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    onClick = {

                    },
                    label = { Text(it.label) }
                )
            }
        }
        HorizontalDivider()
        Row(modifier.wrapContentHeight()) {
            CountryList(
                items = items,
                selectable = selectable,
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
                onCheckedChange = { country, isChecked ->
                    if (isChecked) {
                        selectedItems.add(country)
                    } else {
                        selectedItems.remove(country)
                    }
                }
            )
        }
    }
}
