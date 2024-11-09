package frgp.utn.edu.ar.quepasa.data.model.media

import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp
import java.util.UUID

data class Picture(
    val id: UUID,
    val description: String,
    val active: Boolean = true,
    val mediaType: String,
    val uploadedAt: Timestamp,
    val owner: User?
)