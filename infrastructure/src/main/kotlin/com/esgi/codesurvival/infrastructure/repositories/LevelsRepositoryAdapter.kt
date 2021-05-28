package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.domain.level.Level
import com.esgi.codesurvival.infrastructure.mappers.to
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LevelsRepositoryAdapter @Autowired constructor(
    private val levelRepository: LevelsRepository,
    private val constraintRepository: ConstraintsRepository,
    private val regexesRepository: RegexesRepository,
    private val languagesRepository: LanguagesRepository
    ) : ILevelRepository {

    override fun save(level: Level): Int {
        levelRepository.save(level.to())
        level.constraints.forEach { constraintModel ->
            constraintRepository.save(constraintModel.to(level))
            constraintModel.regexes.forEach { regexModel ->
                regexesRepository.save(regexModel.to(constraintModel))
            }
        }
        return level.ordinalValue
    }

    override fun getNextId(): Int {
        return (levelRepository.count()+1).toInt()
    }

    override fun findById(id: Int): Level? {
        return levelRepository.findByIdOrNull(id)?.to()
    }

    override fun findCompleteById(id: Int): Level? {
        val savedLevel = levelRepository.findByIdOrNull(id)?.to() ?: return null

        val savedConstraints = constraintRepository.findByLevelId(savedLevel.ordinalValue).map { it.to() }
        for (constraintModel in savedConstraints) {
            val savedRegexes = regexesRepository.findByConstraintId(constraintModel.id)
            for (savedRegex in savedRegexes) {
                val savedLanguage = languagesRepository.findByIdOrNull(savedRegex.languageId)
                constraintModel.regexes.add(savedRegex.to(savedLanguage!!.to())) // sorry
            }
            savedLevel.constraints.add(constraintModel)
        }

        return savedLevel
    }
}