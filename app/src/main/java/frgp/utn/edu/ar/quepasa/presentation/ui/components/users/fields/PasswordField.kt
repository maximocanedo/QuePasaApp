package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.presentation.ui.components.editables.OutlinedField
import quepasa.api.validators.users.PasswordValidator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String = "",
    validator: (String) -> PasswordValidator = { PasswordValidator(it) },
    onChange: (String, Boolean) -> Unit,
    serverError: String? = null,
    clearServerError: () -> Unit = {},
    label: String = "Contraseña"
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedField<PasswordValidator, String>(
        modifier = modifier,
        validator = validator,
        valueConverter = { it },
        value = value,
        textConverter = { it },
        clearServerError = clearServerError,
        onChange = onChange,
        serverError = serverError,
        label = label,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if(!showPassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        trailingIcon = {
            IconToggleButton(
                checked = showPassword,
                onCheckedChange = { showPassword = it }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = if(!showPassword) R.drawable.visibility_24dp_5f6368_fill0_wght400_grad0_opsz24__1_ else R.drawable.visibility_off),
                    contentDescription = ""
                )
            }
        }
    )
}

@Preview
@Composable
fun SignupPasswordFieldPreview() {
    var password by remember { mutableStateOf("Hallo") }
    PasswordField(
        modifier = Modifier,
        validator = {
            PasswordValidator(it)
                .ifNullThen("")
                .isNotBlank()
                .hasOneDigit()
                .hasOneLowerCaseLetter()
                .hasOneUpperCaseLetter()
                .hasOneSpecialCharacter()
                .lengthIsEightCharactersOrMore()
        },
        onChange = { value, valid -> password = value },
        value = password
    )
}