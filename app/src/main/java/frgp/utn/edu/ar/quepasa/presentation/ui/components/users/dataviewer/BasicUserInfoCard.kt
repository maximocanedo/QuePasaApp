package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.emergent.AddressEmergentField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.emergent.NameEmergentField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.emergent.NeighbourhoodEmergentField
import java.sql.Timestamp
import java.util.UUID

@Composable
fun BasicUserInfoCard(
    user: User,
    modifier: Modifier = Modifier,
    onNameUpdateRequest: suspend (String) -> Unit,
    onAddressUpdateRequest: suspend (String) -> Unit,
    onNeighbourhoodUpdateRequest: suspend (Neighbourhood) -> Unit
) {
    var nameChanging by remember { mutableStateOf(false) }
    var addressChanging by remember { mutableStateOf(false) }
    var neighbourhoodChanging by remember { mutableStateOf(false) }
    var showing by remember { mutableStateOf(false) }

    if(showing) {
        ListItem(
            headlineContent = { Text(user.id.toString()) },
            supportingContent = { Text("Id") },
            leadingContent = {
                Icon(
                    painterResource(id = R.drawable.tag),
                    contentDescription = "Id"
                )
            }
        )
        ListItem(
            headlineContent = { Text(user.username) },
            supportingContent = { Text("Nombre de usuario") },
            leadingContent = {
                Icon(
                    painterResource(id = R.drawable.alternate_email),
                    contentDescription = "Nombre de usuario"
                )
            }
        )
        ListItem(
            headlineContent = { Text(user.name) },
            supportingContent = { Text("Nombre") },
            modifier = Modifier.clickable {
                nameChanging = true
            },
            leadingContent = {
                Icon(
                    painterResource(id = R.drawable.person),
                    contentDescription = "Nombre"
                )
            }
        )
        ListItem(
            headlineContent = { Text(user.role.name) },
            supportingContent = { Text("Rol") },
            leadingContent = {
                Icon(
                    painterResource(id = R.drawable.badge),
                    contentDescription = "Rol"
                )
            }
        )
        ListItem(
            headlineContent = { Text(user.address) },
            supportingContent = { Text("Dirección") },
            modifier = Modifier.clickable {
                addressChanging = true
            },
            leadingContent = {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = "Dirección"
                )
            }
        )
        ListItem(
            headlineContent = {
                Text(
                    if (user.neighbourhood != null)
                        "${user.neighbourhood.name}, ${user.neighbourhood.city.name}"
                    else ""
                )
            },
            supportingContent = { Text("Barrio asociado") },
            modifier = Modifier.clickable {
                neighbourhoodChanging = true
            },
            leadingContent = {
                Icon(
                    painterResource(id = R.drawable.location_city),
                    contentDescription = "Barrio asociado"
                )
            }
        )
    }

    ListItem(
        modifier = Modifier.clickable {
            showing = !showing
        },
        headlineContent = {
            Text(if(showing) "Ocultar información básica" else "Información básica")
        },
        leadingContent = {
            Icon(
                painter = painterResource(
                    if(showing) R.drawable.keyboard_arrow_up
                    else R.drawable.keyboard_arrow_down
                ),
                contentDescription = if(showing) "Ocultar" else "Mostrar"
            )
        }
    )
    HorizontalDivider(Modifier.fillMaxWidth())

    if(nameChanging) NameEmergentField(
        placeholder = user.name,
        onDismissRequest = { nameChanging = false },
        onRequest = onNameUpdateRequest
    )
    if(addressChanging) AddressEmergentField(
        placeholder = user.address,
        onDismissRequest = { addressChanging = false },
        onRequest = onAddressUpdateRequest
    )
    if(neighbourhoodChanging) NeighbourhoodEmergentField(
        placeholder = user.neighbourhood,
        onDismissRequest = { neighbourhoodChanging = false },
        onRequest = onNeighbourhoodUpdateRequest
    )
}

@Preview @Composable
fun BasicUserInfoCardPreview() {
    val user = User(
        name = "Máximo Canedo",
        picture = Picture(
            id = UUID.fromString("c7d6a327-12f4-4b8d-a81f-2059dd340fe7"),
            description = "",
            mediaType = "image/jpeg",
            uploadedAt = Timestamp(1000000),
            owner = null
        ),
        id = 338,
        username = "root",
        active = true,
        address = "",
        role = Role.ADMIN,
        neighbourhood = null,
        email = emptySet(),
        phone = emptySet()
    )
    BasicUserInfoCard(
        user,
        modifier = Modifier,
        onNameUpdateRequest = {  },
        onAddressUpdateRequest = {  },
        onNeighbourhoodUpdateRequest = {  })
}