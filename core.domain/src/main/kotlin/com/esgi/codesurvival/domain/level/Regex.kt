package com.esgi.codesurvival.domain.level

import java.util.*
import kotlin.text.Regex

class Regex(
    val id: UUID,
    val value: Regex,
    val language: Language
) {
}