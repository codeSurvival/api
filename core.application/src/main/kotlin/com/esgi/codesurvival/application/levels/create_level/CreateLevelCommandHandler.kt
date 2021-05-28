package com.esgi.codesurvival.application.levels.create_level

import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.domain.level.Level
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


class CreateLevelCommand(var turnObjective: Int) : Request<Int>

@Component
class CreateLevelCommandHandler @Autowired constructor(
    private val repository: ILevelRepository
) : RequestHandler<CreateLevelCommand, Int>
{
    override fun handle(request: CreateLevelCommand): Int {
        val newLevel = Level(
            repository.getNextId(),
            request.turnObjective,
            mutableListOf())

        return repository.save(newLevel)
    }

}