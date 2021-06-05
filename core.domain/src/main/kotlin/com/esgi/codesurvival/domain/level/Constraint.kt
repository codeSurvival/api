package com.esgi.codesurvival.domain.level

import com.esgi.codesurvival.domain.code.Algorithm
import java.util.*

class Constraint(
    val id : UUID,
    val name: String,
    val warning: String,
    val regexes: MutableList<Regex>
) {

    fun isRespected(algo: Algorithm) : Boolean {
        regexes.forEach { regex ->
            val match = regex.value.containsMatchIn(algo.code)
            if (regex.language.id == algo.language.id && match) {
                return false
            }
        }
        return true
    }
}