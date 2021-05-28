package com.esgi.codesurvival.domain.level

import com.esgi.codesurvival.domain.common.exceptions.DomainException


class Level(val ordinalValue: Int, val turnObjective: Int, val constraints: MutableList<Constraint>) {

    fun addConstraint( constraint: Constraint) {
        if (constraint.name in constraints.map { it.name }) {
            throw DomainException("Constraints must be unique by name")
        }
        if (constraint.name.trim().isEmpty()) {
            throw DomainException("Constraints must have a name")
        }
        if (constraint.warning.trim().isEmpty()) {
            throw DomainException("Constraints must have a warning")
        }
        if (constraint.regexes.isEmpty()) {
            throw DomainException("Constraints must contain at least one regex")
        }
        if (constraint.regexes.any { it.language.name.trim().isEmpty() }) {
            throw DomainException("Regexes need a language identifier")
        }
        if (constraint.regexes.any { it.value.toString().trim().isEmpty() }) {
            throw DomainException("Regexes must have a content")
        }
        constraints.add(constraint)
    }


}