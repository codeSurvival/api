package com.esgi.codesurvival.domain.level

import com.esgi.codesurvival.domain.common.exceptions.DomainException


class Level(
    val ordinalValue: Int,
    val turnObjective: Int,
    val constraints: MutableList<Constraint>
) {



    fun canAddConstraint( constraint: Constraint): Boolean {
        if (constraint.name in constraints.map { it.name }) {
            return false
        }
        return true
    }


}