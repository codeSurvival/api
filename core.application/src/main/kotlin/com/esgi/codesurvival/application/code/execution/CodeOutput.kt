package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.domain.code.CodeResult

data class CodeOutput (
    var success: Boolean,
    var failedConstraints: List<LightConstraint>
)

fun CodeResult.to() = CodeOutput(success, failedConstraints.map { it.to() })