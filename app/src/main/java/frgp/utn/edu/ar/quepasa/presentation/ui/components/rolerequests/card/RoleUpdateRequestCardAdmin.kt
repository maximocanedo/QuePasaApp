package frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.card

import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.request.RoleUpdateRequest
import frgp.utn.edu.ar.quepasa.presentation.ui.components.rolerequests.dialog.RoleUpdateRespondDialog
import frgp.utn.edu.ar.quepasa.utils.role.roleToSpanish
import java.util.UUID

@Composable
fun RoleUpdateRequestCardAdmin(
    request: RoleUpdateRequest,
    onUpdateRequest: (UUID, Boolean, String) -> Unit,
    onDeleteRequest: (UUID) -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("") }
    val approve = remember { mutableStateOf(false) }
    val delete = remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Text(
                    text = "Solicitud de rol",
                    modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )

                if(request.requestedRole != null) {
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.baseline_supervised_user_circle_24),
                            contentDescription = "Role Image",
                        )
                        Text(
                            text = "Rol: ${roleToSpanish(request.requestedRole.name)}",
                            modifier = Modifier.padding(6.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }

                Row {
                    Icon(
                        painter = painterResource(R.drawable.baseline_comment_24),
                        contentDescription = "Role Comment",
                    )
                    Text(
                        text = "Comentario: ${request.remarks}",
                        modifier = Modifier.padding(6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Row {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Role Reviewer",
                    )
                    val reviewer = if(request.reviewer != null) request.reviewer.name else "No asignado"
                    Text(
                        text = "Revisor: $reviewer",
                        modifier = Modifier.padding(6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Row {
                    if(request.status == RequestStatus.WAITING) {
                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(horizontal = 1.dp),
                            onClick = {
                                approve.value = true
                                delete.value = false
                                title.value = "Aprobar"
                                openDialog.value = true
                            }) {
                                Text("Aprobar"
                            )
                        }
                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(horizontal = 1.dp),
                            onClick = {
                                approve.value = false
                                delete.value = false
                                title.value = "Rechazar"
                                openDialog.value = true
                            }
                        ) {
                            Text("Rechazar")
                        }
                    }
                    Button(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 1.dp),
                        onClick = {
                            delete.value = true
                            title.value = "Eliminar"
                            openDialog.value = true
                        }
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }

    when {
        openDialog.value -> {
            request.id?.let {
                RoleUpdateRespondDialog(
                    onDismissRequest = { openDialog.value = false },
                    id = it,
                    title = title.value,
                    approve = approve.value,
                    delete = delete.value,
                    onUpdateRequest = onUpdateRequest,
                    onDeleteRequest = onDeleteRequest
                )
            }
        }
    }
}

@Preview
@Composable
fun RoleUpdateRequestCardAdminPreview() {
    val roleUpdateRequest = RoleUpdateRequest()
    RoleUpdateRequestCardAdmin(
        request = roleUpdateRequest,
        onUpdateRequest = { id, approve, remark -> println("Working $id $approve $remark")},
        onDeleteRequest = {}
    )
}