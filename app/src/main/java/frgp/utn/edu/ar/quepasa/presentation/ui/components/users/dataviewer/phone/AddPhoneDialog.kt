package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.domain.context.feedback.LocalFeedback
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.MailField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.PhoneField
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import quepasa.api.validators.users.MailValidator
import quepasa.api.validators.users.PhoneValidator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhoneDialog(
    onRequest: suspend (String) -> Unit,
    onDismiss: () -> Unit
) {
    val co = rememberCoroutineScope()
    var phone by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }
    val feedback by LocalFeedback.current.collectAsState()
    var mutableFeedback = LocalFeedback.current
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(true)
    ) {
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
        Row(modifier = rowModifier) {
            Text(
                text = "Agregar número de teléfono",
                textAlign = Center,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Row(modifier = rowModifier) {
            PhoneField(
                modifier = Modifier.fillMaxWidth(),
                onChange = { value, valid ->
                    isValid = valid
                    phone = value
                },
                serverError = if (feedback?.field == "phone") feedback?.message else null,
                clearServerError = { mutableFeedback.update { null } }
            )
        }
        Row(modifier = rowModifier, horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    co.launch { onRequest(phone) }
                    onDismiss()
                },
                enabled = isValid
            ) {
                Text("Agregar")
            }
        }
    }
}