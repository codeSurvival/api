package com.esgi.codesurvival.application.levels.create_level

import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.security.ApplicationException
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional


class UpdateLevelCommand(val ordinalValue : Int, val turnObjective: Int) : Request<Int>

@Component
@Transactional
open class UpdateLevelCommandHandler @Autowired constructor(
    private val repository: ILevelRepository
) : RequestHandler<UpdateLevelCommand, Int>
{
    override fun handle(request: UpdateLevelCommand): Int {
        repository.findById(request.ordinalValue)?.let { level ->
            level.turnObjective = request.turnObjective
            return repository.save(level)
        }
        throw ApplicationException("Level not found")
    }

}