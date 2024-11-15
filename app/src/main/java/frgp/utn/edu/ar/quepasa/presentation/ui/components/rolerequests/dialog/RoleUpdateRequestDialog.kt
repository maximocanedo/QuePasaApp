package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.UUID

@Composable
fun RoleUpdateRequestDialog(
    onDismissRequest: () -> Unit,
    id: UUID,
    title: String,
    onUpdateRequest: (UUID) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title)

                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                Text("¿Estás seguro?")

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                Row {
                    Button(onClick = { onUpdateRequest(id) }) {
                        Text("Sí")
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    Button(onClick = { onDismissRequest() }) {
                        Text("No")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RoleUpdateRequestDialogPreview() {
    val id by remember { mutableStateOf(UUID.randomUUID()) }
    val title by remember { mutableStateOf("") }
    RoleUpdateRequestDialog(
        onDismissRequest = {},
        id = id ,
        title = title,
        onUpdateRequest = {}
    )
}