package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.levels.repositories.IConstraintsRepository
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.infrastructure.mappers.to
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConstraintRepositoryAdapter @Autowired constructor(
    private val constraintsRepository: ConstraintsRepository) : IConstraintsRepository {

    override fun getNextId(): UUID {
        return UUID.randomUUID()
    }

    override fun findById(id: UUID): Constraint? {
        return constraintsRepository.findByIdOrNull(id)?.to()
    }


}