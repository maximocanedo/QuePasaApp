package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.MailField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import quepasa.api.validators.users.MailValidator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMailDialog(
    onRequest: suspend (String) -> Unit,
    onDismiss: suspend () -> Unit,
    state: SheetState = rememberModalBottomSheetState(true)
) {
    val co = rememberCoroutineScope()
    var mail by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = { co.launch { onDismiss() } }
    ) {
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
        Row(modifier = rowModifier) {
            Text(
                text = "Agregar correo",
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Row(modifier = rowModifier) {
            MailField(validator = {
                MailValidator(it)
                    .isNotNull()
                    .isNotBlank()
                    .isValidAddress()
            }, onChange = { value, valid ->
                isValid = valid
                mail = value
            })
        }
        Row(modifier = rowModifier, horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    co.launch { onRequest(mail) }
                },
                enabled = isValid
            ) {
                Text("Agregar")
            }
        }
    }
}