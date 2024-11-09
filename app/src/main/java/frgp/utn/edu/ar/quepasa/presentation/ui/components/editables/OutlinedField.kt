package frgp.utn.edu.ar.quepasa.presentation.ui.components.editables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.VisualTransformation
import quepasa.api.exceptions.ValidationError
import quepasa.api.validators.commons.builders.ValidatorBuilder


@Composable
fun <ValidatorClass: ValidatorBuilder<ValidatorClass, T>, T> OutlinedField(
    modifier: Modifier = Modifier,
    value: T,
    validator: (T) -> ValidatorClass,
    onChange: (T, Boolean) -> Unit,
    serverError: String?,
    clearServerError: () -> Unit,
    label: String,
    textConverter: (T) -> String,
    valueConverter: (String) -> T,
    debounceDelay: Long = 0,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null
) {
    var isValid by remember { mutableStateOf(serverError == null) }
    var errorMessage by remember { mutableStateOf(serverError ?: "") }
    var text by remember { mutableStateOf(textConverter(value)) }
    var debounceText by remember { mutableStateOf(text) }
    var isTouched by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { newText ->
            if(!isTouched) isTouched = true
            text = newText
            debounceText = newText
            clearServerError()
        },
        isError = isTouched && (!isValid || serverError != null),
        label = { Text(label) },
        supportingText = { if (isTouched) Text(serverError ?: errorMessage) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon
    )

    LaunchedEffect(debounceText) {
        kotlinx.coroutines.delay(debounceDelay)
        val newValue = valueConverter(debounceText)
        try {
            validator(newValue).build()
            isValid = true
            errorMessage = ""
        } catch (e: ValidationError) {
            isValid = false
            errorMessage = e.errors.first()
        }
        onChange(newValue, isValid)
    }

    LaunchedEffect(value) {
        val convertedValue = textConverter(value)
        if (convertedValue != text) {
            text = convertedValue
            isValid = serverError == null
        }
    }
}
