package com.esgi.codesurvival.rabbit

import com.esgi.codesurvival.application.rabbit.RabbitEmitter
import org.springframework.stereotype.Component

@Component
data class CodeEmitter(val sender: Sender): RabbitEmitter<String> {
    override fun emit(message: String) {
        sender.send(message)
    }
}