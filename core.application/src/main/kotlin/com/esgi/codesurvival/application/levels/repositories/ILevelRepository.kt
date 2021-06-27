package com.esgi.codesurvival.application.levels.repositories

import com.esgi.codesurvival.domain.code.LevelConstraint
import com.esgi.codesurvival.domain.level.Level
import java.util.*


interface ILevelRepository {
    fun findAll(): List<Level>
    fun save(level: Level): Int
    fun getNextId(): Int
    fun findById(id: Int) : Level?
    fun findCompleteById(id: Int): Level?
    fun findByConstraintId(id: UUID) : Level?
    fun findCompleteByConstraintId(constraintId: UUID): Level?
    fun findAllConstraintsByLevelId(level: Int): List<LevelConstraint>?
}