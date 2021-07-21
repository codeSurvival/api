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

class CreateConstraintCommand(var levelId: Int, var name: String, var warning: String) : Request<UUID>

@Component
@Transactional
open class AddConstraintCommandHandler @Autowired constructor(
    private val constraintRepository: IConstraintsRepository,
    private val levelRepository: ILevelRepository
) : RequestHandler<CreateConstraintCommand, UUID>
{
    override fun handle(request: CreateConstraintCommand): UUID {
        val existingLevel = levelRepository.findById(request.levelId) ?: throw ApplicationException("Level not found")


        val newConstraint = Constraint(
            constraintRepository.getNextId(),
            request.name,
            request.warning,
            mutableListOf())

        if (! existingLevel.canAddConstraint(newConstraint)) {
            throw ApplicationException("Constraint name should be unique")
        }

        existingLevel.constraints.add(newConstraint)
        levelRepository.save(existingLevel)

        return newConstraint.id
    }
}