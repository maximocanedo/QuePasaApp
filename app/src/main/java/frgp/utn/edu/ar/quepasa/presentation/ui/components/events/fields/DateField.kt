package frgp.utn.edu.ar.quepasa.presentation.ui.components.events.fields

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.MutableStateFlow
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.events.EventDateValidator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    modifier: Modifier = Modifier,
    value: LocalDateTime,
    validator: (LocalDateTime) -> EventDateValidator = { EventDateValidator(it) },
    onChange: (LocalDateTime, Boolean) -> Unit,
    label: String,
    isError: Boolean,
    errorMessage: MutableStateFlow<String?>,
) {
    var isValid by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf<LocalDateTime?>(value) }
    var showDateModal by remember { mutableStateOf(false) }
    var showTimeModal by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            OutlinedTextField(
                value = selectedDate?.let { selectedDate!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) }
                    ?: "",
                onValueChange = {
                    selectedDate =
                        LocalDateTime.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                },
                label = { Text(label) },
                placeholder = { Text("MM/DD/YYYY HH:MM") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                },
                modifier = modifier
                    .pointerInput(selectedDate) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showDateModal = true
                            }
                        }
                    },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(errorMessage.collectAsState().value ?: "")
                    }
                }
            )
        }
    }

    if (showDateModal) {
        DateDialog(
            onDismiss = { showDateModal = false },
            onConfirm = { localDateTime ->
                selectedDate = localDateTime
                // Validate date
                try {
                    validator(selectedDate!!).build()
                    isValid = true
                    errorMessage.value = ""
                } catch (e: ValidationError) {
                    isValid = false
                    errorMessage.value = e.errors.first() ?: ""
                }
                if (localDateTime != null) {
                    onChange(localDateTime, isValid)
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
                // Validate date
                try {
                    validator(selectedDate!!).build()
                    isValid = true
                    errorMessage.value = ""
                } catch (e: ValidationError) {
                    isValid = false
                    errorMessage.value = e.errors.first() ?: ""
                }
                if (selectedDate != null) {
                    onChange(selectedDate!!, isValid)
                }
                showTimeModal = false
            }
        )
    }
}
