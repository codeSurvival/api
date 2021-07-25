package com.esgi.codesurvival.application.sse

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.sse.jackets.SseEventType
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseHandler {
    fun subscribeToSse(id: String): SseEmitter?
    fun emitGameEventTo(userId: String, gameEventSerialized: String, emissionType: SseEventType)
    fun emitStep(userId: String, step: CompilationStep)
    fun emitMessageTo(userId: String, message: String)
}