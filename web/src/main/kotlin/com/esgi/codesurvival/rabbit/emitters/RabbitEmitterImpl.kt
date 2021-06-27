package com.esgi.codesurvival.rabbit.emitters

import com.esgi.codesurvival.application.rabbit.RabbitEmitter
import com.esgi.codesurvival.application.rabbit.Sender
import com.esgi.codesurvival.domain.user.UserId
import com.esgi.codesurvival.dtos.CodeParametersDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitEmitterImpl(@Autowired private val codeSender: Sender) : RabbitEmitter<String> {

    override var queueName = "coco"

    override fun emitToRunner(code: String, userId: UserId, turnObjective: Int) {
        val m = jacksonObjectMapper()

        val codeParameters = CodeParametersDTO(code, userId.value.toString(), turnObjective)
        codeSender.send(m.writeValueAsString(codeParameters), queueName)
    }
}