package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.infrastructure.models.LanguageEntity

fun LanguageEntity.to() = Language(
    id = id,
    name = name
)

fun Language.to() = LanguageEntity(
    id = id,
    name = name
)