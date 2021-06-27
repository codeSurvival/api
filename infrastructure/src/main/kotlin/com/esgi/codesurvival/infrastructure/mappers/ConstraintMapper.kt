package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.code.LevelConstraint
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Level
import com.esgi.codesurvival.infrastructure.models.ConstraintEntity

fun ConstraintEntity.toConstraint() = Constraint(
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



fun ConstraintEntity.toLevelConstraint() = LevelConstraint(
    level = levelId,
    constraintId = id,
    name= name,
    warning = warning,
    regexes = mutableListOf()
)
