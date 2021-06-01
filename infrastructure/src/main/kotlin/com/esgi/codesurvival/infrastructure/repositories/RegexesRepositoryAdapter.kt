package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.levels.repositories.IConstraintsRepository
import com.esgi.codesurvival.application.levels.repositories.IRegexRepository
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Regex
import com.esgi.codesurvival.infrastructure.mappers.to
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegexesRepositoryAdapter @Autowired constructor(
    private val regexesRepository: RegexesRepository,
    private val languagesRepository: LanguagesRepository) : IRegexRepository {

    override fun getNextId(): UUID {
        return UUID.randomUUID()
    }

    override fun findById(id: UUID): Regex? {
        var regexEntity =  regexesRepository.findByIdOrNull(id) ?: return null
        var language = languagesRepository.findByIdOrNull(regexEntity.languageId)?.to()

        return regexEntity.to(language!!)
    }
}