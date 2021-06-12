package com.esgi.codesurvival.rabbit

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
data class Sender(@Autowired val template: RabbitTemplate?) {

    fun send(message: String) {
        template!!.convertAndSend("coco", message.toByteArray())
        println(" [x] Sent '$message'")
    }
}