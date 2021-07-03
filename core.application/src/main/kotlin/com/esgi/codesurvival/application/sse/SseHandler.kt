package com.esgi.codesurvival.application.sse

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseHandler {
    fun subscribeToGameEventListening(id: String): SseEmitter?
    fun emitGameEventTo(userId: String, gameEventSerialized: String)
}