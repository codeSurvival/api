package com.esgi.codesurvival.rabbit

import com.esgi.codesurvival.application.rabbit.RabbitConsumer
import com.esgi.codesurvival.application.sse.EventSchedulerHandler
import com.esgi.codesurvival.dtos.GameEventDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component

@Component
class GameEventReceiver(val eventSchedulerHandler: EventSchedulerHandler) : RabbitConsumer<ByteArray> {
    private val mapper = jacksonObjectMapper()

    override fun consume(message: ByteArray) {
        try {
            val g = mapper.readValue<GameEventDTO>(String(message))
            eventSchedulerHandler.addEvent(g.user, String(message))
        } catch (e: Error) {
            println(e.stackTrace)
        }
    }
}