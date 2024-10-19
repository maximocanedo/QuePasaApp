package frgp.utn.edu.ar.quepasa.data.model.media


import frgp.utn.edu.ar.quepasa.data.model.User
import java.util.UUID

data class Document(
    var id: UUID? = null,
    var owner: User? = null,
    var description: String? = null,
    var location: String? = null,
    var isActive: Boolean = true
)








