package com.esgi.codesurvival.rabbit

import com.esgi.codesurvival.application.rabbit.Sender
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
data class CodeSender(@Autowired val template: RabbitTemplate?): Sender {

    override fun send(message: String, queueName: String) {
        template!!.convertAndSend(queueName, message.toByteArray())
        println(" [x] Sent '$message'")
    }
}