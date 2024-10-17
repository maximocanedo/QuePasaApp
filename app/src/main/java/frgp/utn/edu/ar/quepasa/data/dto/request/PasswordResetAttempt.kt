package frgp.utn.edu.ar.quepasa.data.dto.request

import java.util.UUID

data class PasswordResetAttempt(
    val id: UUID,
    val code: String,
    val newPassword: String
)
