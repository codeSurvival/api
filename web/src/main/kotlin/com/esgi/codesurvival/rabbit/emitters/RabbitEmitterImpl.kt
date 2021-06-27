package com.esgi.codesurvival.rabbit.emitters

import com.esgi.codesurvival.application.rabbit.RabbitEmitter
import com.esgi.codesurvival.application.rabbit.Sender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitEmitterImpl(@Autowired private val codeSender: Sender) : RabbitEmitter<String> {

    override var queueName = "coco"

    override fun emitToRunner(message: String) {
        codeSender.send(message, queueName)
    }
}