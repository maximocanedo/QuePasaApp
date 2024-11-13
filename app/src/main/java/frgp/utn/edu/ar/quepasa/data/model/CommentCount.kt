package frgp.utn.edu.ar.quepasa.data.model

import java.sql.Timestamp

data class CommentCount(
    var count: Int,
    var lastUpdated: Timestamp
)