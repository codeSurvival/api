package com.esgi.codesurvival.application.code.execution

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.code.compilation.CompilationStepType
import com.esgi.codesurvival.application.code.compilation.commands.CompilationStepCommand
import com.esgi.codesurvival.application.code.execution.anonymizer.AnonymizerFactory
import com.esgi.codesurvival.application.code.repositories.ICodeOwnerRepository
import com.esgi.codesurvival.application.code.repositories.ICodeRepository
import com.esgi.codesurvival.application.code.repositories.ICompilationStepRepository
import com.esgi.codesurvival.application.languages.repositories.ILanguagesRepository
import com.esgi.codesurvival.application.levels.repositories.ILevelRepository
import com.esgi.codesurvival.application.rabbit.EmitterFactory
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.code.Code
import com.esgi.codesurvival.domain.code.Player
import com.esgi.codesurvival.domain.comparable_code.ComparableCode
import io.jkratz.mediator.core.Mediator
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

class CodeExecutionCommand(var code: String, var languageId: UUID, var username: String) : Request<CodeOutput>


@Component
@Transactional
open class CodeExecutionCommandHandler(
    private val userRepository: IUsersRepository,
    private val levelRepository: ILevelRepository,
    private val languageRepository: ILanguagesRepository,
    private val compilationStepRepository: ICompilationStepRepository,
    private val codeRepository: ICodeRepository,
    private val anonymizerFactory: AnonymizerFactory,
    private val codeOwnerRepository: ICodeOwnerRepository,
    private val codeEmitterFactory: EmitterFactory,
    private val mediator: Mediator,
) :
    RequestHandler<CodeExecutionCommand, CodeOutput> {
    override fun handle(request: CodeExecutionCommand): CodeOutput {
        val user = userRepository.findByUsername(request.username) ?: throw ApplicationException("User not found")
        val compilationStep = compilationStepRepository.getUserCompilationStep(user.id.value)

        if (compilationStep != null &&
            !listOf(CompilationStepType.NONE, CompilationStepType.DONE)
                .contains(compilationStep.compilationType)
        ) {
            throw ApplicationException("Code is already being processed")
        }

        var returnData = CodeOutput(true, mutableListOf(), true)


        mediator.dispatch(
            CompilationStepCommand(
                CompilationStep(CompilationStepType.PARSING),
                user.id.value.toString()
            )
        )

        val submittedAlgorithm = Algorithm(request.code, request.languageId)

        val constraints =
            levelRepository.findAllConstraintsByLevelId(user.level) ?: throw ApplicationException("Level not found")
        val player = Player(user.username, constraints)
        val codeToTest = Code(submittedAlgorithm, player)
        val rulesResult = codeToTest.validate()

        returnData.rulesSuccess = rulesResult.success
        returnData.failedConstraints = rulesResult.failedConstraints.map { it.to() }

        if (!rulesResult.success) {
            return returnData
        }
        val previousCode = codeOwnerRepository.getUserPreviousCode(user.id)

        if (previousCode != null && previousCode.languageId == submittedAlgorithm.languageId) {

            val comparable = ComparableCode(submittedAlgorithm.code)
            val isTooSimilar = comparable.isSimilar(previousCode.code, 0.9)

            if (isTooSimilar) {
                returnData.similaritySuccess = false
        mediator.dispatch(
            CompilationStepCommand(
                CompilationStep(CompilationStepType.NONE),
                user.id.value.toString()
            )
        )
                return returnData
            }
        }

        val codeId = codeRepository.save(submittedAlgorithm)
        user.lastCodeId = codeId
        userRepository.save(user)
        val language =
            languageRepository.findById(request.languageId) ?: throw ApplicationException("Language not found")

        val anonymizer = anonymizerFactory.get(language)


        val userLevel = levelRepository.findById(user.level) ?: throw ApplicationException("level not found")

        mediator.dispatch(
            CompilationStepCommand(
                CompilationStep(CompilationStepType.COMPILATION),
                user.id.value.toString()
            )
        )
        codeEmitterFactory.get(language)
            .emitToRunner(codeToTest.algorithm.code, user.id, userLevel.turnObjective)

        return returnData
    }
}