package frgp.utn.edu.ar.quepasa.data.dto.request

data class PasswordResetRequest(
    val email: String,
    val username: String,
    val phone: String
)
