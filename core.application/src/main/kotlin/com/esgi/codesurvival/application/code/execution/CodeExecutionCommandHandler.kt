package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.rabbit.EmitterFactory
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
    private val rabbitEmitter: RabbitEmitter<String>,
    private val codeEmitterFactory: EmitterFactory
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

        codeEmitterFactory.get(language)
            .emitToRunner(codeToTest.algorithm.code)

        return result.to()
    }
}