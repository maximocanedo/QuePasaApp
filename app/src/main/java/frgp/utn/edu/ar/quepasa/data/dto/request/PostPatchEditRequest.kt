package frgp.utn.edu.ar.quepasa.data.dto.request

data class PostPatchEditRequest (
    val title: String?,
    val subtype: Int?,
    val description: String?,
    val neighbourhood: Long?,
    val tags: String?
)