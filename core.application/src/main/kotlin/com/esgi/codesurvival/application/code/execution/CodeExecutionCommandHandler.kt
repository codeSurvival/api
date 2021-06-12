package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.application.code.execution.compilator.CompilatorFactory
import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.rabbit.RabbitConsumer
import com.esgi.codesurvival.application.rabbit.RabbitEmitter
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.code.Code
import com.esgi.codesurvival.domain.code.Player
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

class CodeExecutionCommand(var code: String, var language: UUID, var username: String) : Request<CodeOutput>


@Component
class CodeExecutionCommandHandler(
    private val userRepository: IUsersRepository,
    private val levelRepository: ILevelRepository,
    private val languageRepository: ILanguagesRepository,
    private val compilatorFactory: CompilatorFactory,
    private val rabbitEmitter: RabbitEmitter<String>
) :
    RequestHandler<CodeExecutionCommand, CodeOutput> {
    override fun handle(request: CodeExecutionCommand): CodeOutput {
        val user = userRepository.findByUsername(request.username) ?: throw ApplicationException("User not found")
        val level = levelRepository.findCompleteById(user.level) ?: throw ApplicationException("Level not found")
        val language = languageRepository.findById(request.language) ?: throw ApplicationException("Language not found")
        val player = Player(user.username, level)
        val codeToTest = Code(Algorithm(request.code, language), player)
        val result = codeToTest.validate()

        if(!result.success) {
            return result.to()
        }

        rabbitEmitter.emit(codeToTest.algorithm.code)

        val compilator = compilatorFactory.get(language)

        val compilatorPaths = compilator.buildEntrypoint()
        compilator.addUserCode(codeToTest.algorithm.code, compilatorPaths)
        compilator.compileAndExecute(compilatorPaths)
        compilator.clean(compilatorPaths)


        return result.to()
    }
}