package com.esgi.codesurvival.application.languages.repositories

import com.esgi.codesurvival.domain.level.Language
import java.util.*


interface ILanguagesRepository {

    fun findAll(): List<Language>
    fun save(language: Language): UUID
    fun getNextId(): UUID
    fun findById(id: UUID) : Language?
}