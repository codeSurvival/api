package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.code.repositories.ICodeRepository
import com.esgi.codesurvival.application.levels.repositories.IConstraintsRepository
import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.user.UserId
import com.esgi.codesurvival.infrastructure.models.CodeEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class CodeRepositoryAdapter @Autowired constructor(
    private val codeRepository: CodeRepository) : ICodeRepository {

    override fun save(code: Algorithm): UUID {
        return codeRepository.save(CodeEntity(getNextId(), code.code, code.languageId )).id
    }


    fun getNextId(): UUID {
        return UUID.randomUUID()
    }
}