package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.dialog.DateDialog
import frgp.utn.edu.ar.quepasa.presentation.ui.components.events.dialog.TimeDialog
import quepasa.api.validators.events.EventDateValidator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    modifier: Modifier = Modifier,
    value: LocalDateTime,
    validator: (LocalDateTime) -> EventDateValidator = { EventDateValidator(it) },
    clearServerError: () -> Unit = {},
    onChange: (LocalDateTime, Boolean) -> Unit,
    serverError: String? = null,
    label: String
) {
    var selectedDate by remember { mutableStateOf<LocalDateTime?>(value) }
    var showDateModal by remember { mutableStateOf(false) }
    var showTimeModal by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            OutlinedTextField(
                value = selectedDate?.let { selectedDate!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) }
                    ?: "",
                onValueChange = {

                },
                label = { Text(label) },
                placeholder = { Text("MM/DD/YYYY HH:MM") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .pointerInput(selectedDate) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showDateModal = true
                            }
                        }
                    },
            )
        }
    }

    if (showDateModal) {
        DateDialog(
            onDismiss = { showDateModal = false },
            onConfirm = { localDateTime ->
                selectedDate = localDateTime
                if (localDateTime != null) {
                    onChange(localDateTime, validator(localDateTime).isValid)
                }
                showDateModal = false
                showTimeModal = true
            }
        )
    }
    if (showTimeModal) {
        TimeDialog(
            onDismiss = { showTimeModal = false },
            onConfirm = { time: TimePickerState ->
                selectedDate = selectedDate?.withHour(time.hour)?.withMinute(time.minute)
                if (selectedDate != null) {
                    onChange(selectedDate!!, validator(selectedDate!!).isValid)
                }
                showTimeModal = false
            }
        )
    }
}
