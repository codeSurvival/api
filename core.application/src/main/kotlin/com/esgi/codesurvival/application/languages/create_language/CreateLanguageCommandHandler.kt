package com.esgi.codesurvival.application.languages.create_language

import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Language
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


class CreateLanguageCommand(var name: String) : Request<UUID>

@Component
class CreateLanguageCommandHandler @Autowired constructor(
    private val repository: ILanguagesRepository
) : RequestHandler<CreateLanguageCommand, UUID>
{
    override fun handle(request: CreateLanguageCommand): UUID {
        val existingLanguages = repository.findAll()
        val newLanguage = Language(
            repository.getNextId(),
            request.name)
        if (!newLanguage.isUnique(existingLanguages)) {
            throw ApplicationException("conflict")
        }
        return repository.save(newLanguage)
    }

}