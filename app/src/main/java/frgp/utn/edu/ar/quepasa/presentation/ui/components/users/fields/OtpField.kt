package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    isError: Boolean = false,
    onOtpTextChange: (String, Boolean) -> Unit,
    onClearError: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }
    val shakeOffset by animateDpAsState(
        targetValue = if (isError) 8.dp else 0.dp,
        animationSpec = keyframes {
            durationMillis = 300
            0.dp at 0
            (-8).dp at 50
            8.dp at 100
            (-8).dp at 150
            8.dp at 200
            0.dp at 300
        }
    )

    BasicTextField(
        modifier = modifier.offset(x = shakeOffset),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                        isError = isError
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isError: Boolean
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(36.dp)
            .background(
                color = if (isError) MaterialTheme.colorScheme.errorContainer
                else when {
                    isFocused -> MaterialTheme.colorScheme.surfaceContainerHigh
                    else -> MaterialTheme.colorScheme.surfaceContainer
                },
                shape = RoundedCornerShape(4.dp)
            )
            .padding(2.dp, 6.dp),
        text = char,
        style = MaterialTheme.typography.headlineMedium,
        color = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun pr() {
    var otpValue by remember { mutableStateOf("123456") }
    var isError by remember { mutableStateOf(false) }

    OtpTextField(
        otpText = otpValue,
        isError = isError,
        onOtpTextChange = { value, otpInputFilled ->
            otpValue = value
        },
        onClearError = { isError = false }
    )

    LaunchedEffect(Unit) {
        if (otpValue == "123456") {
            isError = true
           // otpValue = ""
        }
    }
}
