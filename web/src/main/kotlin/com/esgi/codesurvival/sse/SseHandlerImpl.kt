package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.sse.SseHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class SseHandlerImpl: SseHandler {

    val gameEventEmitters: HashMap<String, MutableList<SseEmitter>> = HashMap()

    override fun subscribeToGameEventListening(id: String): SseEmitter? {
        val emitter = SseEmitter()

        if (!gameEventEmitters.containsKey(id)) {
            gameEventEmitters[id] = mutableListOf()
        }

        gameEventEmitters[id]?.add(emitter)
        return emitter
    }


    override fun emitGameEventTo(userId: String, gameEventByteArray: String) {
        if (gameEventEmitters.containsKey(userId)) {
            for (emitter in gameEventEmitters[userId]!!) {
                emitter.send(gameEventByteArray)
            }
        }
    }

}