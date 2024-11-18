package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.data.model.enums.SubnationalDivisionDenomination
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision

@Composable
fun CityListItem(
    data: City,
    modifier: Modifier,
    showGeographicalContext: Boolean = true,
    selectable: Boolean = false,
    selected: Boolean = false,
    onSelect: (City, Boolean) -> Unit,
    onClick: (City) -> Unit,
    enabled: Boolean = true
    ) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth().clickable { if(selectable) onSelect(data, !selected) else onClick(data) },
            headlineContent = { Text(data.name) },
            supportingContent = {
                if(showGeographicalContext)
                    Text(data.subdivision.label + ", " + data.subdivision.country.label)
            },
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
fun CityListItemPreview() {
    val n = City(
        name = "Tigre",
        id = 34L,
        active = true,
        subdivision = SubnationalDivision(
            label = "Buenos Aires",
            active = true,
            denomination = SubnationalDivisionDenomination.PROVINCE,
            iso3 = "AR-B",
            country = Country(
                iso3 = "ARG",
                label = "Argentina",
                active = true
            )
        )
    )
    CityListItem(
        data = n,
        modifier = Modifier,
        showGeographicalContext = true,
        selectable = true,
        selected = false,
        onSelect = { city, checked ->  },
        onClick = {  }
    )
}