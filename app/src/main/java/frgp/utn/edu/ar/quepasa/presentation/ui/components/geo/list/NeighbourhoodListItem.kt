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
import frgp.utn.edu.ar.quepasa.data.model.enums.SubnationalDivisionDenomination
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision

@Composable
fun NeighbourhoodListItem(
    data: Neighbourhood,
    modifier: Modifier,
    showGeographicalContext: Boolean = true,
    selectable: Boolean = false,
    selected: Boolean = false,
    onSelect: (Neighbourhood, Boolean) -> Unit,
    onClick: (Neighbourhood) -> Unit,
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
                    Text(data.city.name + ", " + data.city.subdivision.label + ", " + data.city.subdivision.country.label)
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
fun NeighbourhoodListItemPreview() {
    val n = Neighbourhood(
        name = "Tigre Joven",
        id = 1,
        active = true,
        city = City(
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
    )
    NeighbourhoodListItem(
        data = n,
        modifier = Modifier,
        showGeographicalContext = true,
        selectable = true,
        selected = false,
        onSelect = { neighbourhood, checked ->  },
        onClick = {  }
    )
}