package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.phone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import java.sql.Timestamp
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhonesCard(
    phones: Set<Phone>,
    modifier: Modifier = Modifier,
    // Mail Dialog events:
    onPhoneRegistration: suspend (String) -> Unit,
    onPhoneDeleteRequest: suspend (Phone) -> Unit,
    onPhoneValidationRequest: suspend (Phone, String) -> Boolean
) {
    var addPhoneDialogState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Text(
        text = "Números de teléfono",
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(12.dp)
    )
    phones.forEach {
        var showDialog by remember { mutableStateOf(false) }
        ListItem(
            headlineContent = { Text(it.phone) },
            supportingContent = { if(!it.verified) Text("Sin verificar") },
            modifier = Modifier.clickable { showDialog = true }
        )
        if(showDialog) PhoneDialog(
            phone = (it),
            onDismissRequest = { showDialog = false },
            onDeleteRequest = onPhoneDeleteRequest,
            onValidateRequest = onPhoneValidationRequest
        )
    }
    ListItem(
        headlineContent = { Text("Agregar número de teléfono") },
        leadingContent = {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Agregar número de teléfono"
            )
        },
        modifier = Modifier.clickable {
            addPhoneDialogState = true
        }
    )
    if(addPhoneDialogState) AddPhoneDialog(
        onRequest = onPhoneRegistration,
        onDismiss = { addPhoneDialogState = false }
    )
}

@Preview
@Composable
fun PhonesCardPreview() {
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
    PhonesCard(
        phones = setOf(
            Phone(
                phone = "+54 11 3038-8784",
                user = user,
                requestedAt = Timestamp(System.currentTimeMillis() - 102410241024),
                verified = true,
                verifiedAt = Timestamp(System.currentTimeMillis())
            ),
            Phone(
                phone = "+54 11 3035-5889",
                user = user,
                requestedAt = Timestamp(System.currentTimeMillis() - 102410241024),
                verified = false,
                verifiedAt = Timestamp(System.currentTimeMillis())
            )
        ),
        onPhoneValidationRequest = { phone, code -> true },
        onPhoneDeleteRequest = { phone -> },
        onPhoneRegistration = { phoneNumber -> }
    )
}