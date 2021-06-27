package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.infrastructure.models.CodeEntity

fun CodeEntity.to() = Algorithm(value, languageId)