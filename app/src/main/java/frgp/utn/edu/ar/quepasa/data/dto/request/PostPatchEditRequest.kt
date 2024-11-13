package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.enums.Audience

data class PostPatchEditRequest(
    val audience: Audience?,
    val title: String?,
    val subtype: Int?,
    val description: String?,
    val neighbourhood: Long?,
    val tags: String?
)