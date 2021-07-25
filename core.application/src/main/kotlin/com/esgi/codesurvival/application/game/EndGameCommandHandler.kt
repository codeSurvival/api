package com.esgi.codesurvival.application.code

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.code.compilation.CompilationStepType
import com.esgi.codesurvival.application.code.compilation.commands.CompilationStepCommand
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.sse.SseMessage
import com.esgi.codesurvival.application.sse.jackets.SseEventType
import com.esgi.codesurvival.application.sse.jackets.SseEventType.MESSAGE
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.UserId
import io.jkratz.mediator.core.Mediator
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

class EndGameCommand(val gameover: Boolean, val gameLoss: Boolean, val user: String) : Request<Unit>

@Component
@Transactional
open class EndGameCommandHandler(
    val userRepository: IUsersRepository,
    val levelRepository: ILevelRepository,
    val mediator: Mediator,
    val sseHandler: SseHandler
) : RequestHandler<EndGameCommand, Unit> {
    override fun handle(request: EndGameCommand) {
        val userUUID = UUID.fromString(request.user)
        val user = userRepository.findById(UserId(userUUID)) ?: throw ApplicationException("No user")
        mediator.dispatch(CompilationStepCommand(CompilationStep(CompilationStepType.DONE), request.user))
        if (!request.gameLoss) {
            val levels = levelRepository.findAll()

            if (levels.map { level -> level.ordinalValue }.contains(user.level.plus(1))) {
                user.level += 1
                userRepository.save(user)
            }
        }

        sseHandler.emitTo<String>(
            request.user,
            (if (request.gameLoss) SseMessage.LOSE_GAME else SseMessage.WIN_GAME).message,
            MESSAGE
        )
    }
}
