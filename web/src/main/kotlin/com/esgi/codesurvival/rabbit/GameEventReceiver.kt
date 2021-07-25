package com.esgi.codesurvival.rabbit

import com.esgi.codesurvival.application.code.EndGameCommand
import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.code.compilation.CompilationStepType
import com.esgi.codesurvival.application.code.compilation.commands.CompilationStepCommand
import com.esgi.codesurvival.application.rabbit.RabbitConsumer
import com.esgi.codesurvival.application.sse.EventSchedulerHandler
import com.esgi.codesurvival.dtos.GameEventDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jkratz.mediator.core.Mediator
import org.springframework.stereotype.Component

@Component
class GameEventReceiver(val eventSchedulerHandler: EventSchedulerHandler, val mediator: Mediator) : RabbitConsumer<ByteArray> {
    private val mapper = jacksonObjectMapper()

    override fun consume(message: ByteArray) {
        try {
            println(String(message))
            val g = mapper.readValue<GameEventDTO>(String(message))
            mediator.dispatch(CompilationStepCommand(CompilationStep(CompilationStepType.RUNNING), g.user))
            eventSchedulerHandler.addEvent(g.user, String(message))
        } catch (e: Error) {
            println(e.stackTrace)
        }
    }
}