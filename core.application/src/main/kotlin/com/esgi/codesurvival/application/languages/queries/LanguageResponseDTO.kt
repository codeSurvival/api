package com.esgi.codesurvival.application.languages.queries

import com.esgi.codesurvival.domain.level.Language
import java.util.*

class LanguageResponseDTO (
    val id : UUID,
    val name : String
        )


fun Language.to() = LanguageResponseDTO(
    id = id,
    name = name
)