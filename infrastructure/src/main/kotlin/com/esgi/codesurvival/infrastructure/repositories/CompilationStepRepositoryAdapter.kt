package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.code.repositories.ICompilationStepRepository
import com.esgi.codesurvival.infrastructure.mappers.to
import com.esgi.codesurvival.infrastructure.models.CompilationStepEntity
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
open class CompilationStepRepositoryAdapter: ICompilationStepRepository {

    val usersCompilationStep: MutableMap<UUID, CompilationStepEntity> = mutableMapOf()

    override fun getUserCompilationStep(userId: UUID): CompilationStep? {
        return usersCompilationStep[userId]?.to()
    }

    override fun add(userId: UUID, step: CompilationStep) {
        usersCompilationStep[userId] = step.to()
    }
}

