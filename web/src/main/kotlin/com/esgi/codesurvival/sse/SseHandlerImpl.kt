package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.sse.SseHandler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event


@Component
@EnableScheduling
class SseHandlerImpl : SseHandler {

    val gameEventEmitters: HashMap<String, MutableList<SseEmitter>> = HashMap()
    val ghostEmitters: HashMap<String, MutableList<SseEmitter>> = HashMap()

    override fun subscribeToGameEventListening(id: String): SseEmitter? {
        val emitter = SseEmitter(-1L)

        if (!gameEventEmitters.containsKey(id)) {
            gameEventEmitters[id] = mutableListOf()
        }

        gameEventEmitters[id]?.add(emitter)
        return emitter
    }


    override fun emitGameEventTo(userId: String, gameEventSerialized: String) {
        if (gameEventEmitters.containsKey(userId)) {
            for (emitter in gameEventEmitters[userId]!!) {
                try {
                    this.emit(emitter, userId, gameEventSerialized)
                } catch (e: Error) {
                    println(e)
                }
            }
        }
        this.cleanEmitters()
    }

    private fun cleanEmitters() {
        for ((key, value) in ghostEmitters) {
            for (emitter in value) {
                this.gameEventEmitters[key]?.remove(emitter)
            }
        }

        this.ghostEmitters.clear()
    }

    @Scheduled(fixedRate = 30000)
    fun keepAlive() {
        for ((key, value) in gameEventEmitters) {
            for (emitter in value) {
                this.emit(emitter, key, "")
            }
        }
        this.cleanEmitters()
    }


    private fun emit(emitter: SseEmitter, userId: String, emission: String) {
        val event = event()
        event.data(emission)

        kotlin.runCatching {
            emitter.send(event)
        }.onFailure {
            if (!ghostEmitters.containsKey(userId)) {
                ghostEmitters[userId] = mutableListOf()
            }
            ghostEmitters[userId]?.add(emitter)
        }
    }

}