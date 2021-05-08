package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.application.runners.JavascriptRunner
import com.esgi.codesurvival.application.runners.Runner
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.code.Code
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component

class CodeExecutionCommand(var code: String, var language: String, var username: String) : Request<CodeOutput>


@Component
class CodeExecutionCommandHandler(private val userRepository: IUsersRepository) :
    RequestHandler<CodeExecutionCommand, CodeOutput> {
    override fun handle(request: CodeExecutionCommand): CodeOutput {
        val user = userRepository.findByUsername(request.username)
        val runner: Runner

        if (request.language == "JS") {
            runner = JavascriptRunner()
            return runner.run(Code(request.code))
        }

        throw ApplicationException("Language not supported")

    }


}