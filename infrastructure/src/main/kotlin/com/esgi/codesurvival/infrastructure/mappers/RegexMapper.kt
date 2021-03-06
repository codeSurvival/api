package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.code.ConstraintRegex
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.domain.level.Regex
import com.esgi.codesurvival.infrastructure.models.RegexEntity

fun RegexEntity.toRegex(language : Language) = Regex(
    id = id,
    value = pattern.toRegex(),
    language = language
)

fun Regex.to(constraint : Constraint) = RegexEntity(
    id = id,
    pattern = value.toString(),
    languageId = language.id,
    constraintId = constraint.id
)

fun RegexEntity.toConstraintRegex() = ConstraintRegex(
    value = pattern.toRegex(),
    languageId = languageId
)