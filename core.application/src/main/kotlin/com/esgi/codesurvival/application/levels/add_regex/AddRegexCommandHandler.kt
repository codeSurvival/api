package com.esgi.codesurvival.application.levels.add_regex

import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.repositories.IConstraintsRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.levels.repositories.IRegexRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Regex
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

class CreateRegexCommand(var constraintId: UUID, var value: String, var languageId: UUID) : Request<UUID>

@Component
@Transactional
open class AddRegexCommandHandler @Autowired constructor(
    private val levelRepository: ILevelRepository,
    private val regexRepository: IRegexRepository,
    private val languageRepository: ILanguagesRepository
) : RequestHandler<CreateRegexCommand, UUID>
{
    override fun handle(request: CreateRegexCommand): UUID {

        val existingLanguage = languageRepository.findById(request.languageId) ?: throw ApplicationException("Language not found")
        val existingLevel = levelRepository.findCompleteByConstraintId(request.constraintId) ?: throw ApplicationException("Constraint or Level not found")

        val newRegex = Regex(
            regexRepository.getNextId(),
            request.value.toRegex(),
            existingLanguage)

        existingLevel.constraints.find{ c -> c.id == request.constraintId}!!.regexes.add(newRegex)
        levelRepository.save(existingLevel)

        return newRegex.id
    }
}