package com.esgi.codesurvival.application.rabbit

interface RabbitConsumer<T> {
    fun consume(message: T)
}