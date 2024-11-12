package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.readonly.ReadOnlyUserField

@Deprecated("Usar {ProfileScreen.kt}")
@Composable
fun UserProfileScreen(navController: NavHostController, user: User?) { // TODO: Change to User (non-nullable) after login is implemented
    BaseComponent(navController, user, "Usuario", false) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(BorderStroke(1.dp, Color.LightGray))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp
            )) {
                Text(text = "Nombre real", fontSize = 18.sp)
                ReadOnlyUserField(value = "Nombre Ejemplo")
                Text(text = "Nombre de usuario", fontSize = 18.sp)
                ReadOnlyUserField(value = "ejemplo-username")
                Row {
                    Icon(Icons.Filled.MailOutline, "User Mail")
                    Text(text = "Correo electrónico", fontSize = 18.sp)
                }
                ReadOnlyUserField(value = "ejemplo@mail.com")
                Row {
                    Icon(Icons.Filled.Phone, "User Phone")
                    Text(text = "Teléfono", fontSize = 18.sp)
                }
                ReadOnlyUserField(value = "123456789")
                Row {
                    Icon(Icons.Filled.Place, "User Place")
                    Text(text = "Barrio", fontSize = 18.sp)
                }
                ReadOnlyUserField(value = "Ejemplo Barrio")
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* TODO: Not implemented yet */ } ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "User Edit",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* TODO: Not implemented yet */ } ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "User Delete",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}