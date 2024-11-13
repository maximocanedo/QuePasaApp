package frgp.utn.edu.ar.quepasa.domain.context.user

import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role

class AuthenticationContext(
    val user: User? = null
) {

    val username: String
        get() { return user?.username ?: "" }

    val name: String
        get() { return user?.name ?: "" }

    val id: Int?
        get() { return user?.id }

    val ok: Boolean
        get() { return isPresent() }

    fun isPresent(): Boolean = user != null
    fun isEmpty(): Boolean = user == null

    val isAdmin: Boolean
        get() { return user?.role == Role.ADMIN }

    val isModerator: Boolean
        get() { return isAdmin || user?.role == Role.MOD }

    val isGovernment: Boolean
        get() { return isModerator || user?.role == Role.GOVT }

    val isOrganization: Boolean
        get() { return isModerator || user?.role == Role.ORGANIZATION }

    val isGroup: Boolean
        get() { return isGovernment || isOrganization }

    val isContributor: Boolean
        get() { return isGovernment || user?.role == Role.CONTRIBUTOR }

    val isNeighbour: Boolean
        get() { return isGroup || isContributor || user?.role == Role.NEIGHBOUR }

    val isUser: Boolean
        get() { return isNeighbour || user?.role == Role.USER }

}

fun User?.toContext(): AuthenticationContext {
    return AuthenticationContext(this)
}