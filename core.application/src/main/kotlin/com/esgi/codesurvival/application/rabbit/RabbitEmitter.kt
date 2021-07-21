package com.esgi.codesurvival.application.rabbit

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.domain.user.UserId

interface RabbitEmitter<T> {
    var queueName: String
    fun emitToRunner(code: T, userId: UserId, turnObjective: Int)
    fun emitStep(step: CompilationStep, userId: String)
}