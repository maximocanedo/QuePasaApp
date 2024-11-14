package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.utils.role.roleToEnglish
import frgp.utn.edu.ar.quepasa.utils.role.rolesToSpanishHigherThan

@Composable
fun RoleRequesterField(
    modifier: Modifier,
    onItemSelected: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    val user by LocalAuth.current.collectAsState()

    val roles: List<String> = user.user?.let { rolesToSpanishHigherThan(it.role) } ?: emptyList()

    var selectedItem by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                value = selectedItem,
                onValueChange = {},
                placeholder = { Text("Rol")},
                readOnly = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_supervised_user_circle_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                roles.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem = item
                            onValidityChange(true)
                            onItemSelected(roleToEnglish(item).name)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RoleRequesterFieldPreview() {
    RoleRequesterField(modifier = Modifier, onItemSelected = {}, onValidityChange = {})
}