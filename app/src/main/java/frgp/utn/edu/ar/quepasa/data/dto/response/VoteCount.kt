package frgp.utn.edu.ar.quepasa.data.dto.response

import java.sql.Timestamp

data class VoteCount(
    val votes: Int,
    val userVote: Int,
    val updated: Timestamp
)
