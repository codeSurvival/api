package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.domain.code.LevelConstraint
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Level
import com.esgi.codesurvival.infrastructure.mappers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class LevelsRepositoryAdapter @Autowired constructor(
    private val levelRepository: LevelsRepository,
    private val constraintRepository: ConstraintsRepository,
    private val regexesRepository: RegexesRepository,
    private val languagesRepository: LanguagesRepository
    ) : ILevelRepository {

    override fun findAll(): List<Level> {
        return levelRepository.findAll().map { it.to() }
    }

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

        val savedConstraints = constraintRepository.findByLevelId(savedLevel.ordinalValue).map { it.toConstraint() }
        for (constraintModel in savedConstraints) {
            val savedRegexes = regexesRepository.findByConstraintId(constraintModel.id)
            for (savedRegex in savedRegexes) {
                val savedLanguage = languagesRepository.findByIdOrNull(savedRegex.languageId)
                constraintModel.regexes.add(savedRegex.toRegex(savedLanguage!!.to())) // sorry
            }
            savedLevel.constraints.add(constraintModel)
        }

        return savedLevel
    }

    override fun findByConstraintId(id: UUID): Level? {
        val constraint = constraintRepository.findByIdOrNull(id) ?: return null
        return this.findById(constraint.levelId)
    }


    override fun findCompleteByConstraintId(constraintId: UUID): Level? {
        val constraint = constraintRepository.findByIdOrNull(constraintId) ?: return null
        return findCompleteById(constraint.levelId)
    }

    override fun findAllConstraintsByLevelId(level: Int): List<LevelConstraint> {
        levelRepository.findByIdOrNull(level)?.to() ?: return mutableListOf()

        val savedConstraints =  constraintRepository.findAllByLevelIdGreaterThanEqual(level).map { it.toLevelConstraint() }
        for (constraint in savedConstraints) {
            val savedRegexes = regexesRepository.findByConstraintId(constraint.constraintId)
            for (savedRegex in savedRegexes) {
                constraint.regexes.add(savedRegex.toConstraintRegex())
            }
        }

        return savedConstraints
    }

    override fun getActiveConstraints(levelId: Int): List<Constraint> {
        levelRepository.findByIdOrNull(levelId)?.to() ?: return mutableListOf()
        return constraintRepository.findAllByLevelIdGreaterThanEqual(levelId).map { it.toConstraint() }
    }
}