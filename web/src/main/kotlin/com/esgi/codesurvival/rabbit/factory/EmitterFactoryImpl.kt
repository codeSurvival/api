package com.esgi.codesurvival.rabbit.factory

import com.esgi.codesurvival.application.rabbit.EmitterFactory
import com.esgi.codesurvival.application.rabbit.RabbitEmitter
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Language
import org.springframework.stereotype.Component

@Component
class EmitterFactoryImpl(private val rabbitEmitter: RabbitEmitter<String>): EmitterFactory {
    override fun get(language: Language): RabbitEmitter<String> {
        when(language.name) {
            "Kotlin" -> rabbitEmitter.queueName = "coco"
            else -> throw ApplicationException("Code not implemented yet")
        }

        return rabbitEmitter
    }

    override fun get(): RabbitEmitter<String> {
         rabbitEmitter.queueName = "steps"

        return rabbitEmitter
    }
}