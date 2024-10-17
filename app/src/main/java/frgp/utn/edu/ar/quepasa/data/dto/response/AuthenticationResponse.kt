package frgp.utn.edu.ar.quepasa.data.dto.response

data class AuthenticationResponse(
    val token: String,
    val totpRequired: Boolean
)
