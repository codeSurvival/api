package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.domain.level.Constraint

data class LightConstraint (val name: String, val warning: String)

fun Constraint.to() = LightConstraint(
    name = name,
    warning = warning
)