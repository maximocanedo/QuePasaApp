package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.data.model.geo.Country

@Composable
fun CountryListItem(
    data: Country,
    modifier: Modifier,
    selectable: Boolean = false,
    selected: Boolean = false,
    onSelect: (Country, Boolean) -> Unit,
    onClick: (Country) -> Unit,
    enabled: Boolean = true
    ) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth().clickable { if(selectable) onSelect(data, !selected) else onClick(data) },
            headlineContent = { Text(data.label) },
            trailingContent = { Text(data.iso3) },
            leadingContent = {
                if(selectable) {
                    Checkbox(
                        checked = selected,
                        onCheckedChange = {
                            onSelect(data, it)
                        },
                        enabled = data.active && enabled
                    )
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun CountryListItemPreview() {
    val n = Country(
        iso3 = "ARG",
        label = "Argentina",
        active = true
    )
    CountryListItem(
        data = n,
        modifier = Modifier,
        selectable = true,
        selected = false,
        onSelect = { country, checked ->  },
        onClick = {  }
    )
}