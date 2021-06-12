package com.esgi.codesurvival.rabbit

import com.esgi.codesurvival.dtos.GameEventDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component

@Component
class GameEventReceiver: RabbitConsumer<ByteArray> {
    private val mapper = jacksonObjectMapper()

    override fun consume(message: ByteArray) {
        val g = mapper.readValue<GameEventDTO>(String(message))
    }
}