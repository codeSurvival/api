package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Level
import com.esgi.codesurvival.infrastructure.models.ConstraintEntity

fun ConstraintEntity.to() = Constraint(
    id = id,
    name = name,
    warning = warning,
    regexes = mutableListOf()
)

fun Constraint.to(level: Level) = ConstraintEntity(
    id = id,
    name = name,
    warning = warning,
    levelId = level.ordinalValue
)
