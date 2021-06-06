package com.esgi.codesurvival.application.levels.queries

import com.esgi.codesurvival.application.languages.queries.LanguageResponseDTO
import com.esgi.codesurvival.application.languages.queries.to
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Level
import com.esgi.codesurvival.domain.level.Regex
import java.util.*

data class CompleteLevelResponseDTO (
    val ordinalValue: Int,
    val turnObjective: Int,
    val constraints : List<ConstraintResponseDTO>
    )

data class ConstraintResponseDTO (
    val id : UUID,
    val name : String,
    val warning : String,
    val regexes : List<RegexResponseDTO>
    )

data class RegexResponseDTO (
    val id: UUID,
    val pattern: String,
    val language : LanguageResponseDTO
        )


fun Level.to() = CompleteLevelResponseDTO(
    ordinalValue = ordinalValue,
    turnObjective = turnObjective,
    constraints = constraints.map { it.to() }
)

fun Constraint.to() = ConstraintResponseDTO(
    id = id,
    name = name,
    warning = warning,
    regexes = regexes.map { it.to() }
)

fun Regex.to() = RegexResponseDTO(
    id = id,
    pattern = value.toString(),
    language = language.to()
)
