package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Picture
import java.sql.Timestamp
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MailsCard(
    mails: Set<Mail>,
    modifier: Modifier = Modifier,
    // Mail Dialog events:
    onMailRegistration: suspend (String) -> Unit,
    onMailDeleteRequest: suspend (Mail) -> Unit,
    onMailValidationRequest: suspend (Mail, String) -> Boolean
) {
    var addMailDialogState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .wrapContentHeight()
    ) {
        Text(
            text = "Correos",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(12.dp)
        )
        mails.forEach {
            var showDialog by remember { mutableStateOf(false) }
            ListItem(
                headlineContent = { Text(it.mail) },
                supportingContent = { if(!it.verified) Text("Sin verificar") },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                ),
                modifier = Modifier.clickable { showDialog = true }
            )
            if(showDialog) MailDialog(
                mail = (it),
                onDismissRequest = { showDialog = false },
                onDeleteRequest = onMailDeleteRequest,
                onValidateRequest = onMailValidationRequest
            )
        }
        OutlinedButton(
            onClick = {
                addMailDialogState = true
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Registrar nueva dirección de correo")
        }
    }
    if(addMailDialogState) AddMailDialog(
        onRequest = onMailRegistration,
        onDismiss = { addMailDialogState = false }
    )
}

@Preview
@Composable
fun MailsCardPreview() {
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
    MailsCard(
        mails = setOf(
            Mail(
                mail= "parravicini@gmail.com",
                user = user,
                requestedAt = Timestamp(System.currentTimeMillis() - 102410241024),
                verified = true,
                verifiedAt = Timestamp(System.currentTimeMillis())
            ),
            Mail(
                mail= "abc@gmail.com",
                user = user,
                requestedAt = Timestamp(System.currentTimeMillis() - 102410241024),
                verified = false,
                verifiedAt = Timestamp(System.currentTimeMillis())
            )
        ),
        onMailValidationRequest = { mail, code -> true },
        onMailDeleteRequest = { mail -> },
        onMailRegistration = { mailAddress -> }
    )
}