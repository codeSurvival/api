package com.esgi.codesurvival.domain.level

import java.util.*

class Language(
    val id: UUID,
    val name: String
) {

    fun isUnique(languages : List<Language>) : Boolean {
        return languages.none { it.name == this.name }
    }
}