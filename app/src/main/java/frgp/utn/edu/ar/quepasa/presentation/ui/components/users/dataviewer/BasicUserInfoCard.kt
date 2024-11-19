package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .wrapContentHeight()
    ) {
        Text(
            text = "Información básica",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(12.dp)
        )
        ListItem(
            headlineContent = { Text(user.id.toString()) },
            supportingContent = { Text("Id") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        )
        ListItem(
            headlineContent = { Text(user.username) },
            supportingContent = { Text("Nombre de usuario") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        )
        ListItem(
            headlineContent = { Text(user.name) },
            supportingContent = { Text("Nombre") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            ),
            modifier = Modifier.clickable {
                nameChanging = true
            }
        )
        ListItem(
            headlineContent = { Text(user.role.name) },
            supportingContent = { Text("Rol") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        )
        ListItem(
            headlineContent = { Text(user.address) },
            supportingContent = { Text("Dirección") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            ),
            modifier = Modifier.clickable {
                addressChanging = true
            }
        )
        ListItem(
            headlineContent = { Text(
                if(user.neighbourhood != null)
                    "${user.neighbourhood.name}, ${user.neighbourhood.city.name}"
                else ""
            ) },
            supportingContent = { Text("Barrio asociado") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            ),
            modifier = Modifier.clickable {
                neighbourhoodChanging = true
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
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