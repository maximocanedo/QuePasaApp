package frgp.utn.edu.ar.quepasa.data.model

import java.io.Serializable

data class Trend(
    val id: Long? = null,
    val tag: String? = null,
    val cantidad: Int = 0
) : Serializable, TrendProjection {
    override fun retrieveTag() = tag
    override fun retrieveCantidad() = cantidad
}





