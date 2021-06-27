package com.esgi.codesurvival.application.rabbit

import com.esgi.codesurvival.domain.user.UserId

interface RabbitEmitter<T> {
    var queueName: String
    fun emitToRunner(code: T, userId: UserId, turnObjective: Int)
}