package com.esgi.codesurvival.application.languages.`create-language`

import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.add_constraint.CreateConstraintCommand
import com.esgi.codesurvival.application.levels.repositories.IConstraintsRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.domain.level.Language
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


class CreateLanguageCommand(var name: String) : Request<UUID>


@Component
class CreateLanguageCommandHandler @Autowired constructor(
    private val languageRepository: ILanguagesRepository
) : RequestHandler<CreateLanguageCommand, UUID> {
    override fun handle(request: CreateLanguageCommand): UUID {
        val language = Language(languageRepository.getNextId(), request.name)

        return languageRepository.save(language)
    }

}