package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.media.Picture

data class UserPatchEditRequest(
    val name: String?,
    val address: String?,
    val neighbourhood: Neighbourhood?,
    val picture: Picture?
)
