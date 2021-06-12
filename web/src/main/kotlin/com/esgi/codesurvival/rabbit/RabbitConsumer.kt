package com.esgi.codesurvival.rabbit

interface RabbitConsumer<T> {
    fun consume(message: T)
}