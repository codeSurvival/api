package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.infrastructure.models.LevelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LevelsRepository : JpaRepository<LevelEntity, Int> {
}