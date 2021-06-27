package com.esgi.codesurvival.domain.code

import java.util.*


class LevelConstraint (val level: Int,
                       val constraintId: UUID,
                       val name: String,
                       val warning: String,
                       val regexes: MutableList<ConstraintRegex>) {

    fun isRespected(algo: Algorithm) : Boolean {
        regexes.forEach { regex ->
            val match = regex.value.containsMatchIn(algo.code)
            if (regex.languageId == algo.languageId && match) {
                return false
            }
        }
        return true
    }
}
