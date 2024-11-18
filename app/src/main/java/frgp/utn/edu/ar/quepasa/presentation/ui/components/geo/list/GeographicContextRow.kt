package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision
import frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.selector.NeighbourhoodSelectorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeographicContextRow(
    modifier: Modifier = Modifier,
    city: City?,
    state: SubnationalDivision?,
    country: Country?,
    tab: NeighbourhoodSelectorScreen,
    onTabUpdateRequest: (NeighbourhoodSelectorScreen) -> Unit
) {
    if(country != null && tab != NeighbourhoodSelectorScreen.COUNTRY)
        Row(modifier.padding(horizontal = 20.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier,
                text = buildAnnotatedString {
                    append("Región: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        when(tab) {
                            NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> {
                                if(city != null) append("${city.name}, ${city.subdivision.label}, ${city.subdivision.country.label}")
                                else if(state != null) append("${state.label}, ${state.country.label}")
                                else append(country!!.label)
                            }
                            NeighbourhoodSelectorScreen.CITY -> {
                                if(state != null) append("${state.label}, ${state.country.label}")
                                else append(country!!.label)
                            }
                            NeighbourhoodSelectorScreen.STATE -> {
                                append(country!!.label)
                            }
                            NeighbourhoodSelectorScreen.COUNTRY -> {}
                        }
                    }
                }
            )
        }
        if(tab != NeighbourhoodSelectorScreen.COUNTRY) Column {
            FilledTonalIconButton(
                onClick = {
                    onTabUpdateRequest(when(tab) {
                        NeighbourhoodSelectorScreen.NEIGHBOURHOOD -> NeighbourhoodSelectorScreen.CITY
                        NeighbourhoodSelectorScreen.CITY -> NeighbourhoodSelectorScreen.STATE
                        NeighbourhoodSelectorScreen.STATE -> NeighbourhoodSelectorScreen.COUNTRY
                        NeighbourhoodSelectorScreen.COUNTRY -> NeighbourhoodSelectorScreen.COUNTRY
                    })
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Editar"
                )
            }
        }
    }
}

@Preview
@Composable
fun GeographicContextRowPreview() {
    var tab by remember { mutableStateOf(NeighbourhoodSelectorScreen.NEIGHBOURHOOD) }
    val n = NeighbourhoodsMDFP.first()
    GeographicContextRow(
        city = n.city,
        state = n.city.subdivision,
        country = n.city.subdivision.country,
        tab = tab,
        onTabUpdateRequest = { tab = it }
    )
}