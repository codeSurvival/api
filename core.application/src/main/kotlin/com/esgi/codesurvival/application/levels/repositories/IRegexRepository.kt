package com.esgi.codesurvival.application.levels.repositories

import com.esgi.codesurvival.domain.level.Regex
import java.util.*

interface IRegexRepository {
    fun getNextId(): UUID
    fun findById(id: UUID) : Regex?
}