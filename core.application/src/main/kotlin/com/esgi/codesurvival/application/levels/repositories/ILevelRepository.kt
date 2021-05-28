package com.esgi.codesurvival.application.levels.repositories

import com.esgi.codesurvival.domain.level.Level


interface ILevelRepository {
    fun save(level: Level): Int
    fun getNextId(): Int
    fun findById(id: Int) : Level?
    fun findCompleteById(id: Int): Level?
}