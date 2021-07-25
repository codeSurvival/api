package com.esgi.codesurvival.application.sse

import com.esgi.codesurvival.application.sse.jackets.SseEventType
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseHandler {
    fun subscribeToSse(id: String): SseEmitter?
    fun <T> emitTo(userId: String, emission: T, emissionType: SseEventType)
}