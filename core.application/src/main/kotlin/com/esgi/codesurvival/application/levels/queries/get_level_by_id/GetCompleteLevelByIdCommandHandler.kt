package com.esgi.codesurvival.application.levels.queries.get_level_by_id

import com.esgi.codesurvival.application.levels.queries.*
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.security.ApplicationException
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component

data class GetCompleteLevelByIdQuery(val id: Int) : Request<CompleteLevelResponseDTO>

@Component
class GetCompleteLevelByIdCommandHandler (private val repository: ILevelRepository)
    : RequestHandler<GetCompleteLevelByIdQuery, CompleteLevelResponseDTO> {

    override fun handle(request: GetCompleteLevelByIdQuery): CompleteLevelResponseDTO {
        this.repository.findCompleteById(request.id)?.let { level ->
            return level.to()
        }
        throw ApplicationException("Level not found")
    }
}