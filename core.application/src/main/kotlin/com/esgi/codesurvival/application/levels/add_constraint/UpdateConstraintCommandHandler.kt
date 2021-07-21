package com.esgi.codesurvival.application.levels.add_constraint

import com.esgi.codesurvival.application.levels.repositories.IConstraintsRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Constraint
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional


class UpdateConstraintCommand(
    var levelId: Int,
    var constraintId: UUID,
    var name: String,
    var warning: String
    ) : Request<UUID>

@Component
@Transactional
open class UpdateConstraintCommandHandler
@Autowired constructor(
    private val levelRepository: ILevelRepository
) : RequestHandler<UpdateConstraintCommand, UUID>
{
    override fun handle(request: UpdateConstraintCommand): UUID {
        val existingLevel = levelRepository.findCompleteById(request.levelId) ?: throw ApplicationException("Level not found")

        val existingConstraint = existingLevel.constraints
            .find { constraint -> constraint.id == request.constraintId }
            ?: throw ApplicationException("Constraint not found")

        existingConstraint.name = request.name
        existingConstraint.warning = request.warning

        levelRepository.save(existingLevel)

        return existingConstraint.id
    }


}