package com.esgi.codesurvival.domain.code

class Code (val algorithm: Algorithm, val player: Player) {


    fun validate() : CodeResult {
        val result = CodeResult(true, mutableListOf<Constraint>())

        val constraints = player.level.constraints
        for (constraint in constraints) {
            if(!constraint.isRespected(algorithm)) {
                result.success = false
                result.failedConstraints.add(constraint)
            }
        }

        return result
    }
}