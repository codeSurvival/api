package com.esgi.codesurvival.application.levels.queries

import com.esgi.codesurvival.application.code.execution.LightConstraint
import com.esgi.codesurvival.domain.level.Constraint
import java.util.*



fun Constraint.toLightConstraintResponseDTO() = LightConstraintResponseDTO(
    id = id,
    name = name,
    warning = warning
)

data class LevelActiveConstraintsResponseDTO (
    val level: LightLevel,
    val constraints : List<LightConstraintResponseDTO>
)

data class LightConstraintResponseDTO (
    val id : UUID,
    val name: String,
    val warning : String
)