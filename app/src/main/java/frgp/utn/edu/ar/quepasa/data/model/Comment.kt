package frgp.utn.edu.ar.quepasa.data.model

import java.sql.Timestamp
import java.util.UUID

data class Comment(
    var id: UUID,
    var content: String,
    var author: User? = null,
    var timestamp: Timestamp,
    var active: Boolean = true
)
