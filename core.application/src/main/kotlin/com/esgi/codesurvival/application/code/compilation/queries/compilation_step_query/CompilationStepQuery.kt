package com.esgi.codesurvival.application.code.compilation

import com.esgi.codesurvival.application.code.repositories.ICompilationStepRepository
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import javax.transaction.Transactional


data class CompilationStepQuery(var username: String) : Request<CompilationStep>

@Component
@Transactional
open class GetCompilationStepQuery(
    val repository: ICompilationStepRepository,
    val userRepository: IUsersRepository,
) :
    RequestHandler<CompilationStepQuery, CompilationStep> {
    override fun handle(request: CompilationStepQuery): CompilationStep {
        val user = userRepository.findByUsername(request.username) ?: throw ApplicationException("User not found")
        return repository.getUserCompilationStep(user.id.value) ?: throw ApplicationException("No Compilation step")
    }
}

