package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import java.sql.Timestamp
import java.util.UUID

@Composable
fun BasicUserInfoCard(user: User, modifier: Modifier = Modifier) {
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
            )
        )
        ListItem(
            headlineContent = { Text(user.role.name) },
            supportingContent = { Text("Rol") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
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
    BasicUserInfoCard(user)
}