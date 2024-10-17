package frgp.utn.edu.ar.quepasa.data.model.auth

import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp
import java.util.UUID

data class SingleUseRequest(
    val id: UUID,
    val action: SingleUseRequestAction,
    val user: User?,
    val requested: Timestamp,
    val active: Boolean
)
