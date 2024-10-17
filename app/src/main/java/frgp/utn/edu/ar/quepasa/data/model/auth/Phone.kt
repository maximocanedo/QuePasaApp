package frgp.utn.edu.ar.quepasa.data.model.auth

import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp

data class Phone(
    val phone: String,
    val verified: Boolean,
    val verifiedAt: Timestamp,
    val requestedAt: Timestamp,
    val user: User?

)
