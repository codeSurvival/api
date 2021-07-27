package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.domain.code.CodeResult

data class CodeOutput (
    var rulesSuccess: Boolean,
    var failedConstraints: List<LightConstraint>,
    var similaritySuccess : Boolean?,
    var previousCode: String? = null
) {
}

fun CodeResult.to() = CodeOutput(success, failedConstraints.map { it.to() }, null)