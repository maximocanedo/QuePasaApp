package frgp.utn.edu.ar.quepasa.data.model

import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import kotlinx.serialization.Serializable

@Serializable
data class TargetNeighbourhood (

    val  id:Int,
    val  event: Event,
    val  target: Neighbourhood

)