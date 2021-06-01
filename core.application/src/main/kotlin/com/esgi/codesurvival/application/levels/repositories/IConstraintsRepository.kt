package com.esgi.codesurvival.application.levels.repositories

import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Level
import java.util.*

interface IConstraintsRepository {
    fun getNextId(): UUID
    fun findById(id: UUID) : Constraint?
}