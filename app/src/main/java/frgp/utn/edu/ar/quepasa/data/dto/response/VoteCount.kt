package frgp.utn.edu.ar.quepasa.data.dto.response

import java.sql.Timestamp

data class VoteCount(
    val votes: Int,
    val uservote: Int,
    val updated: Timestamp
)
