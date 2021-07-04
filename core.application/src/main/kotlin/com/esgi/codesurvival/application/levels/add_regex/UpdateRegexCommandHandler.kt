package com.esgi.codesurvival.application.levels.add_regex

import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.levels.repositories.IRegexRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Regex
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


class UpdateRegexCommand(
    var constraintId: UUID,
    var regexId: UUID,
    var value: String,
    var languageId: UUID
) : Request<UUID>

@Component
class UpdateRegexCommandHandler @Autowired constructor(
    private val levelRepository: ILevelRepository,
    private val regexRepository: IRegexRepository,
    private val languageRepository: ILanguagesRepository
) : RequestHandler<UpdateRegexCommand, UUID> {
    override fun handle(request: UpdateRegexCommand): UUID {
        val existingLanguage = languageRepository.findById(request.languageId) ?: throw ApplicationException("Language not found")
        val existingLevel = levelRepository.findCompleteByConstraintId(request.constraintId) ?: throw ApplicationException("Constraint or Level not found")
        val existingConstraint = existingLevel.constraints.find{ c -> c.id == request.constraintId}?: throw ApplicationException("Constraint not found")
        val existingRegex = existingConstraint.regexes.find { r -> r.id == request.regexId }?: throw ApplicationException("Regex not found")
        existingRegex.language = existingLanguage
        existingRegex.value = request.value.toRegex()

        levelRepository.save(existingLevel)

        return existingRegex.id
    }
}