package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.infrastructure.models.ConstraintEntity
import com.esgi.codesurvival.infrastructure.models.RegexEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RegexesRepository: JpaRepository<RegexEntity, UUID> {

    fun findByConstraintId(constraintId : UUID) : List<RegexEntity>
}