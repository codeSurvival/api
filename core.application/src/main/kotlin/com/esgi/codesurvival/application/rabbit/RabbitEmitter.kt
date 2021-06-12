package com.esgi.codesurvival.application.rabbit

interface RabbitEmitter<T> {
    fun emit(message: T)
}