package com.esgi.codesurvival.application.game.error

import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.sse.jackets.SseEventType.ERROR
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import io.jkratz.mediator.core.Mediator
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import javax.transaction.Transactional

class ErrorHandleCommand(val type: GameErrorType, val message: String, val user: String) : Request<Unit>

@Component
@Transactional
open class ErrorHandleCommandHandler(
    val mediator: Mediator,
    val sseHandler: SseHandler
) : RequestHandler<ErrorHandleCommand, Unit> {
    override fun handle(request: ErrorHandleCommand) {
        sseHandler.emitTo(request.user, GameError(request.type, request.message), ERROR)
    }
}
