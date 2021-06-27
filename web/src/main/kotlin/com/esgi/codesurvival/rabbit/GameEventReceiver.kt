package com.esgi.codesurvival.rabbit

import com.esgi.codesurvival.application.rabbit.RabbitConsumer
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.dtos.GameEventDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component

@Component
class GameEventReceiver(val sseHandler: SseHandler): RabbitConsumer<ByteArray> {
    private val mapper = jacksonObjectMapper()

    override fun consume(message: ByteArray) {
        val g = mapper.readValue<GameEventDTO>(String(message))
        sseHandler.emitGameEventTo(g.user, String(message))
        println(g)
    }
}