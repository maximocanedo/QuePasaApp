package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.dialog

import android.widget.Toast
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun RoleUpdateRespondDialog(
    onDismissRequest: () -> Unit,
    id: UUID,
    title: String,
    approve: Boolean,
    delete: Boolean,
    onUpdateRequest: (UUID, Boolean, String) -> Unit,
    onDeleteRequest: (UUID) -> Unit,
) {
    val context = LocalContext.current
    var remark: String by remember { mutableStateOf("") }
    val dp = if(delete) 200.dp else 250.dp
    
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .height(dp)
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

                if(!delete) {
                    TextField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        value = remark,
                        onValueChange = { newText ->
                            remark = newText
                        },
                        placeholder = { Text("Deje un comentario...") },
                    )
                }
                else {
                    Text("¿Estás seguro?")
                }

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                Row {
                    Button(onClick = {
                        if(!delete) {
                            if(remark.isNotBlank()) {
                                if (approve) {
                                    onUpdateRequest(id, true, remark)
                                } else {
                                    onUpdateRequest(id, false, remark)
                                }
                            }
                            else {
                                CoroutineScope(IO).launch {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Ingrese un comentario", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        else {
                            onDeleteRequest(id)
                        }
                    }) {
                        Text("Aceptar")
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    Button(onClick = { onDismissRequest() }) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RoleUpdateRespondDialogPreview() {
    val id by remember { mutableStateOf(UUID.randomUUID()) }
    val title by remember { mutableStateOf("") }
    RoleUpdateRespondDialog(
        onDismissRequest = {},
        id = id ,
        title = title,
        approve = true,
        delete = false,
        onUpdateRequest = { id, approve, remark -> println("Working $id $approve $remark")},
        onDeleteRequest = {}
    )
}