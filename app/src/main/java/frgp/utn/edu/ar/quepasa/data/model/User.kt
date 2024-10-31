package frgp.utn.edu.ar.quepasa.data.model

import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.auth.Phone
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood


data class User(
    val id: Int,
    val username: String,
    val name: String,
    val phone: Set<Phone>,
    val address: String,
    val neighbourhood: Neighbourhood?,
    // TODO Agregar picture
    val email: Set<Mail>,
    val role: Role,
    val active: Boolean
)
