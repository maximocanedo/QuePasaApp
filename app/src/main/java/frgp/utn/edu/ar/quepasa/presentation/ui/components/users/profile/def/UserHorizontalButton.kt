package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import java.sql.Timestamp
import java.util.UUID


@Composable
fun UserHorizontalButton(user: User, modifier: Modifier = Modifier, onClick: (User) -> Unit) {
    TextButton(onClick = { onClick(user) }) {
        UserHorizontalDesign(user = user, modifier = modifier.height(48.dp).padding(0.dp))
    }
}

@Composable
fun UserHorizontalDesign(user: User, modifier: Modifier = Modifier
    .height(60.dp)
    .padding(6.dp)) {
    val img = if(user.picture == null) null else
        "http://canedo.com.ar:8080/api/pictures/"+user.picture.id+"/view"
    Row(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
        ) {
            Avatar(imageUrl = img, description = user.name)
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 8.dp, end = 8.dp)
                .height(48.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .wrapContentWidth()
            )
            Text(
                text = "@" + user.username,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .wrapContentWidth()
            )
        }
    }
}

@Preview @Composable
fun UserHorizontalButtonPreview() {
    UserHorizontalButton(user = User(
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
    ),
        onClick = {

        })
}