package com.esgi.codesurvival.application.levels.queries.get_level_by_id

import com.esgi.codesurvival.application.levels.queries.LightLevel
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.security.ApplicationException
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import javax.transaction.Transactional

data class GetLevelByIdQuery(val id: Int) : Request<LightLevel>

@Component
@Transactional
open class GetLevelByIdCommandHandler (private val repository: ILevelRepository)
    : RequestHandler<GetLevelByIdQuery, LightLevel>
{
    override fun handle(request: GetLevelByIdQuery): LightLevel {
        this.repository.findById(request.id)?.let { level ->
            return LightLevel(level.ordinalValue, level.turnObjective)
        }
        throw ApplicationException("Level not found")
    }
}