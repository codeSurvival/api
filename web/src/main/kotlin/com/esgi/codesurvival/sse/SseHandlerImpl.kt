package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.sse.SseHandler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event


@Component
@EnableScheduling
class SseHandlerImpl: SseHandler {

    val gameEventEmitters: HashMap<String, MutableList<SseEmitter>> = HashMap()

    override fun subscribeToGameEventListening(id: String): SseEmitter? {
        val emitter = SseEmitter(-1L)
        if (!gameEventEmitters.containsKey(id)) {
            gameEventEmitters[id] = mutableListOf()
        }

        gameEventEmitters[id]?.add(emitter)
        println(gameEventEmitters)

        emitter.onCompletion {
            this.gameEventEmitters.get(id)?.remove(emitter)
            println("remove successful")
        }

        return emitter
    }


    override fun emitGameEventTo(userId: String, gameEventSerialized: String) {
        println(gameEventEmitters)
        if (gameEventEmitters.containsKey(userId)) {
            for (emitter in gameEventEmitters[userId]!!) {
                try {
                     val event = event()
                    event.data(gameEventSerialized)
                    println(event)
                    emitter.send(event)
                } catch (e: Error) {
                    println(e)
                }
            }
        }
    }

    @Scheduled(fixedRate = 30000)
    fun keepAlive() {
        println("yulu")
        for ((key, value) in gameEventEmitters) {
            for (emitter in value) {
                println(value)
                val event = event()
                println("kill the bitch")
                event.data("duguuuu")
                emitter.send(event)
            }
        }

    }


}