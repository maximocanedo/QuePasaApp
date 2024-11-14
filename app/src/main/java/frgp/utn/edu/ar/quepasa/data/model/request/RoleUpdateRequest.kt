package frgp.utn.edu.ar.quepasa.data.model.request

import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.media.Document
import java.util.UUID

data class RoleUpdateRequest (
    val id: UUID? = null,
    val requester: User? = null,
    val requestedRole: Role? = null,
    val documents: Set<Document>? = null,
    val remarks: String? = null,
    val reviewer: User? = null,
    val status: RequestStatus = RequestStatus.WAITING,
    val active: Boolean = false
)