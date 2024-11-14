package frgp.utn.edu.ar.quepasa.utils.role

import frgp.utn.edu.ar.quepasa.data.model.enums.Role

fun roleHigherThan(currentRole: Role, lowerRole: Role): Boolean {
    return currentRole.ordinal > lowerRole.ordinal
}

fun roleLowerThan(currentRole: Role, higherRole: Role): Boolean {
    return currentRole.ordinal < higherRole.ordinal
}