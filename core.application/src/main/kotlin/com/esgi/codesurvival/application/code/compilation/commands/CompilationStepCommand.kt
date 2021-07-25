package com.esgi.codesurvival.application.code.compilation.commands

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.code.repositories.ICompilationStepRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.UserId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional


data class CompilationStepCommand(val step: CompilationStep, val userId: String) : Request<Unit>

@Component
@Transactional
open class CompilationStepCommandHandler(
    val repository: ICompilationStepRepository,
    val userRepository: IUsersRepository,
    val sseHandler: SseHandler
) :
    RequestHandler<CompilationStepCommand, Unit> {
    override fun handle(request: CompilationStepCommand) {
        val userUUID = UUID.fromString(request.userId)
        val user = userRepository.findById(UserId(userUUID)) ?: throw ApplicationException("No user")
        repository.add(userUUID, request.step)
        sseHandler.emitStep(request.userId, request.step)
        return
    }

}

