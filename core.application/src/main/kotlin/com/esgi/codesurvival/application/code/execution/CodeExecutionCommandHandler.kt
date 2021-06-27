package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.application.code.execution.anonymizer.AnonymizerFactory
import com.esgi.codesurvival.application.code.repositories.ICodeOwnerRepository
import com.esgi.codesurvival.application.code.repositories.ICodeRepository
import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.rabbit.EmitterFactory
import com.esgi.codesurvival.application.rabbit.RabbitEmitter
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.code.Code
import com.esgi.codesurvival.domain.code.Player
import com.esgi.codesurvival.domain.comparable_code.ComparableCode
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

class CodeExecutionCommand(var code: String, var languageId: UUID, var username: String) : Request<CodeOutput>


@Component
class CodeExecutionCommandHandler(
    private val userRepository: IUsersRepository,
    private val levelRepository: ILevelRepository,
    private val languageRepository: ILanguagesRepository,
    private val codeRepository: ICodeRepository,
    private val anonymizerFactory: AnonymizerFactory,
    private val codeOwnerRepository: ICodeOwnerRepository,
    private val codeEmitterFactory: EmitterFactory
) :
    RequestHandler<CodeExecutionCommand, CodeOutput> {
    override fun handle(request: CodeExecutionCommand): CodeOutput {
        var returnData = CodeOutput(true, mutableListOf(), true)

        val submittedAlgorithm = Algorithm(request.code, request.languageId)

        val user = userRepository.findByUsername(request.username) ?: throw ApplicationException("User not found")
        val constraints = levelRepository.findAllConstraintsByLevelId(user.level) ?: throw ApplicationException("Level not found")
        val player = Player(user.username, constraints)
        val codeToTest = Code( submittedAlgorithm, player)
        val rulesResult = codeToTest.validate()

        returnData.rulesSuccess = rulesResult.success
        returnData.failedConstraints = rulesResult.failedConstraints.map{ it.to() }

        if(!rulesResult.success) {
            return returnData
        }

        val previousCode = codeOwnerRepository.getUserPreviousCode(user.id)

        if (previousCode != null && previousCode.languageId == submittedAlgorithm.languageId) {

            val comparable = ComparableCode(submittedAlgorithm.code)
            val isTooSimilar = comparable.isSimilar(previousCode.code, 0.9)

            if (isTooSimilar) {
                returnData.similaritySuccess = false
                return returnData
            }
        }

        val codeId = codeRepository.save(submittedAlgorithm)
        user.lastCodeId = codeId
        userRepository.save(user)

//        val anonymizer = anonymizerFactory.get(language) // TODO : use

        val language = languageRepository.findById(request.languageId) ?: throw ApplicationException("Language not found")

        codeEmitterFactory.get(language)
            .emitToRunner(codeToTest.algorithm.code)

        return returnData
    }
}