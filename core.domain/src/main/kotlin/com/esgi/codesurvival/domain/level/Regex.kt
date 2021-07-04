package com.esgi.codesurvival.domain.level

import java.util.*
import kotlin.text.Regex

class Regex(
    val id: UUID,
    var value: Regex,
    var language: Language
) {
}