package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import java.sql.Timestamp

data class PostCreateRequest(
    val originalPoster: String?,
    val audience: Audience?,
    val title: String?,
    val subtype: Int?,
    val description: String?,
    val neighbourhood: Long?,
    val timestamp: Timestamp?,
    val tags: String?
)