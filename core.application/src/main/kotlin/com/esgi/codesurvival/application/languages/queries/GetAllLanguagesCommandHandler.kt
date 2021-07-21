package com.esgi.codesurvival.application.languages.queries

import com.esgi.codesurvival.application.code.execution.CodeOutput
import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.queries.CompleteLevelResponseDTO
import com.esgi.codesurvival.application.levels.queries.get_level_by_id.GetCompleteLevelByIdQuery
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional


class GetAllLanguagesQuery() : Request<List<LanguageResponseDTO>>


@Component
@Transactional
open class GetAllLanguagesCommandHandler (private val repository: ILanguagesRepository)
    : RequestHandler<GetAllLanguagesQuery, List<LanguageResponseDTO>> {
    override fun handle(request: GetAllLanguagesQuery): List<LanguageResponseDTO> {
        return this.repository.findAll().map { it.to() }
    }
}