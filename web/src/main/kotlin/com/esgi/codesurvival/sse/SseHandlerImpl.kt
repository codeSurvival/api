package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.sse.jackets.SseEventType
import com.esgi.codesurvival.application.sse.jackets.SseEventType.*
import com.esgi.codesurvival.application.sse.jackets.SseJacket
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
    private val mapper = jacksonObjectMapper()

    override fun subscribeToSse(id: String): SseEmitter? {
        val emitter = SseEmitter(-1L)

        if (!gameEventEmitters.containsKey(id)) {
            gameEventEmitters[id] = mutableListOf()
        }

        gameEventEmitters[id]?.add(emitter)
        return emitter
    }


    override fun emitTo(userId: String, gameEventSerialized: String, emissionType: SseEventType) {
        if (gameEventEmitters.containsKey(userId)) {
            for (emitter in gameEventEmitters[userId]!!) {
                try {
                    this.emit(emitter, userId, gameEventSerialized, emissionType)
                } catch (e: Error) {
                    println(e)
                }
            }
        }
        this.cleanEmitters()
    }

    override fun emitStep(userId: String, step: CompilationStep) {
        if (gameEventEmitters.containsKey(userId)) {
            val serializedStep = mapper.writeValueAsString(step)
            for (emitter in gameEventEmitters[userId]!!) {
                try {
                    println("step emitted !")
                    this.emit(emitter, userId, serializedStep, COMPILATION_STEP)
                } catch (e: Error) {
                    println(e)
                }
            }
        }
        this.cleanEmitters()    }

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
                this.emit(emitter, key, "", HEARTBEAT)
            }
        }
        this.cleanEmitters()
    }


    private fun emit(emitter: SseEmitter, userId: String, emission: String, emissionType: SseEventType) {
        val event = event()
        event.data(SseJacket(emissionType, emission))

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