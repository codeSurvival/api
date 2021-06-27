package com.esgi.codesurvival.application.levels.queries.get_levels

import com.esgi.codesurvival.application.levels.queries.LightLevel
import com.esgi.codesurvival.application.levels.queries.get_level_by_id.GetLevelByIdQuery
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component


class GetLevelsQuery() : Request<List<LightLevel>>

@Component
class GetLevelsCommandHandler (private val repository: ILevelRepository):
    RequestHandler<GetLevelsQuery, List<LightLevel>> {
    override fun handle(request: GetLevelsQuery): List<LightLevel> {
        return repository.findAll().map { level ->
            LightLevel(level.ordinalValue, level.turnObjective)
        }
    }
}