package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.infrastructure.models.ConstraintEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ConstraintsRepository : JpaRepository<ConstraintEntity, UUID> {

    fun findByLevelId(levelId : Int) : List<ConstraintEntity>

    fun findAllByLevelIdGreaterThanEqual(levelId: Int) : List<ConstraintEntity>
}