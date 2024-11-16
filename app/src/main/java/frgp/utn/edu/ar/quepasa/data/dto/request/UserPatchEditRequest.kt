package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.media.Picture

data class UserPatchEditRequest(
    val name: String? = null,
    val address: String? = null,
    val neighbourhood: Neighbourhood? = null,
    val picture: Picture? = null
)
