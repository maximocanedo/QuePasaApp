package frgp.utn.edu.ar.quepasa.data.model.commenting

import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp

abstract class AbsComment {
    abstract var id: Int
    abstract var content: String
    abstract var author: User?
    abstract var timestamp: Timestamp
    abstract var active: Boolean
}