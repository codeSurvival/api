package com.esgi.codesurvival.application.levels.queries.GetActiveConstraintsByLevel

import com.esgi.codesurvival.application.levels.queries.*
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.security.ApplicationException
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import javax.transaction.Transactional


data class GetActiveConstraintsByLevelQuery(val levelId : Int): Request<LevelActiveConstraintsResponseDTO>

@Component
@Transactional
open class GetActiveConstraintsByLevelQueryHandler(val levelRepository: ILevelRepository) :
    RequestHandler<GetActiveConstraintsByLevelQuery, LevelActiveConstraintsResponseDTO> {


    override fun handle(request: GetActiveConstraintsByLevelQuery): LevelActiveConstraintsResponseDTO {

        var levelDTO: LightLevel? = null
        val constraints = mutableListOf<LightConstraintResponseDTO>()

        this.levelRepository.findById(request.levelId)?.let { level ->
            levelDTO = LightLevel(level.ordinalValue, level.turnObjective)
        }
        if (levelDTO == null) {
            throw ApplicationException("Level not found")
        }

        this.levelRepository.getActiveConstraints(request.levelId)?.let { constraintEntities ->
            constraintEntities.forEach { constraintEntity ->
                constraints.add(constraintEntity.toLightConstraintResponseDTO())
            }
        }

        return LevelActiveConstraintsResponseDTO(levelDTO!!, constraints)
    }


}