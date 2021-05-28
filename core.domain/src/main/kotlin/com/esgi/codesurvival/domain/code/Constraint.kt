package com.esgi.codesurvival.domain.code

class Constraint (val name: String, val regexes : MutableList<Regex>, val warning : String) {

    fun isRespected(algo: Algorithm) : Boolean {
        regexes.forEach { regex ->
            if (regex.language == algo.language && regex.value.containsMatchIn(algo.code)) {
                return false
            }
        }
        return true
    }
}
