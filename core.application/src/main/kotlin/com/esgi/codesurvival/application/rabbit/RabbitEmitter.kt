package com.esgi.codesurvival.application.rabbit

interface RabbitEmitter<T> {
    var queueName: String
    fun emitToRunner(message: T)
}