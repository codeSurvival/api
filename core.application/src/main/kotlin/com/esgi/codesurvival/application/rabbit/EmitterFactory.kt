package com.esgi.codesurvival.application.rabbit

import com.esgi.codesurvival.domain.level.Language

interface EmitterFactory {
    fun get(language: Language): RabbitEmitter<String>
    fun get(): RabbitEmitter<String>
}