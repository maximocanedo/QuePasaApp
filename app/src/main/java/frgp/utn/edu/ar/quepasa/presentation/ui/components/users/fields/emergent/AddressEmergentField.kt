package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.emergent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.EmergentField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.AddressField
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields.NameField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import quepasa.api.validators.commons.StringValidator


@Composable
fun AddressEmergentField(
    placeholder: String? = null,
    onDismissRequest: () -> Unit,
    onRequest: suspend (String) -> Unit,
) {
    val feedback by LocalFeedback.current.collectAsState()
    var mutFeedb = LocalFeedback.current
    var isValid by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf(placeholder ?: "") }
    EmergentField(
        onDismissRequest = onDismissRequest,
        onRequest = { CoroutineScope(IO).launch { onRequest(value) } },
        isValid = isValid,
        title = "Cambiar dirección"
    ) {
        AddressField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            validator = { StringValidator(it).isNotNull().isNotBlank() },
            onChange = { v, valid ->
                isValid = valid
                value = v
            },
            serverError = feedback?.of("address#userPatchEdit"),
            clearServerError = { mutFeedb.update { null } }
        )
    }
}