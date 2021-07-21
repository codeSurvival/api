package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.infrastructure.mappers.to
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class LanguagesRepositoryAdapter @Autowired constructor(
    private val languagesRepository: LanguagesRepository)
    : ILanguagesRepository {
    override fun findAll(): List<Language> {
        return languagesRepository.findAll().map { it.to() }
    }

    @Transactional
    override fun save(language: Language): UUID {
        return languagesRepository.save(language.to()).id
    }

    override fun getNextId(): UUID {
        return UUID.randomUUID()
    }

    override fun findById(id: UUID): Language? {
        return languagesRepository.findByIdOrNull(id)?.to()
    }
}