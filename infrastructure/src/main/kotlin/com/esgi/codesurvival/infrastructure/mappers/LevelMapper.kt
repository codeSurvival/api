package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.level.Level
import com.esgi.codesurvival.infrastructure.models.LevelEntity

fun LevelEntity.to() = Level(
    ordinalValue = id,
    turnObjective = turnObjective,
    constraints = mutableListOf()
)

fun Level.to() = LevelEntity(
    ordinalValue,
    turnObjective
)