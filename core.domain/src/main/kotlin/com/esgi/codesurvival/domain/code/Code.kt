package com.esgi.codesurvival.domain.code

import com.esgi.codesurvival.domain.level.Constraint

class Code (val algorithm: Algorithm, val player: Player) {


    fun validate() : CodeResult {
        val result = CodeResult(true, mutableListOf<LevelConstraint>())

        val constraints = player.constraints
        for (constraint in constraints) {
            if(!constraint.isRespected(algorithm)) {
                result.success = false
                result.failedConstraints.add(constraint)
            }
        }

        return result
    }
}