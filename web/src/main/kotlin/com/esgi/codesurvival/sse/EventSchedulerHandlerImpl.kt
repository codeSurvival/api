package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.code.EndGameCommand
import com.esgi.codesurvival.application.sse.EventSchedulerHandler
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.sse.jackets.SseEventType
import com.esgi.codesurvival.dtos.GameEventDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jkratz.mediator.core.Mediator
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
@EnableScheduling
class EventSchedulerHandlerImpl(val sseHandlerImpl: SseHandler, val mediator: Mediator): EventSchedulerHandler {

    val gameEventsByUserId: HashMap<String, Deque<String>> = HashMap()
    val dequeToRemove : MutableList<String> = mutableListOf()
    private val mapper = jacksonObjectMapper()

    override fun addEvent(userId: String, gameEventString: String) {
        if(!gameEventsByUserId.containsKey(userId)) {
            gameEventsByUserId[userId] = ArrayDeque()
        }

        gameEventsByUserId[userId]?.add(gameEventString)
    }


    @Scheduled(fixedRate = 1000)
    private fun depile() {
        for((key, value) in gameEventsByUserId) {
            if(!value.isEmpty()) {
                val messageStringified = value.pop()
                val g = mapper.readValue<GameEventDTO>(messageStringified)
                if (g.gameover) {
                    mediator.dispatch(EndGameCommand(g.gameover, g.gameLoss, g.user))
                }

                if (g.error != null) {
                    // TODO handle error
                }
                sseHandlerImpl.emitGameEventTo(key, messageStringified, SseEventType.GAME_EVENT)
            } else {
                dequeToRemove.add(key)
            }
        }
        clearDeques()
    }

    private fun clearDeques() {
        for(deque in dequeToRemove) {
            gameEventsByUserId.remove(deque)
        }

        dequeToRemove.clear()
    }
}