package com.esgi.codesurvival.application.game.error

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

class ErrorHandleCommand(val gameError: GameErrorType, val message: String, val user: String) : Request<Unit>

@Component
@Transactional
open class ErrorHandleCommandHandler(
    val userRepository: IUsersRepository,
    val mediator: Mediator,
    val sseHandler: SseHandler
) : RequestHandler<ErrorHandleCommand, Unit> {
    override fun handle(request: ErrorHandleCommand) {
        val userUUID = UUID.fromString(request.user)

    }
}
