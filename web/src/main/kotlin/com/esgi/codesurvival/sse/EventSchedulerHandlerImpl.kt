package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.code.EndGameCommand
import com.esgi.codesurvival.application.game.error.ErrorHandleCommand
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

    val gameEventsByUserId: HashMap<String, Deque<GameEventDTO>> = HashMap()
    val dequeToRemove : MutableList<String> = mutableListOf()
    private val mapper = jacksonObjectMapper()

    override fun addEvent(userId: String, gameEventString: String) {
        if(!gameEventsByUserId.containsKey(userId)) {
            gameEventsByUserId[userId] = ArrayDeque()
        }

        val gameEventDTO = mapper.readValue<GameEventDTO>(gameEventString)

        gameEventsByUserId[userId]?.add(gameEventDTO)
    }


    @Scheduled(fixedRate = 1000)
    private fun depile() {
        for((key, value) in gameEventsByUserId) {
            if(!value.isEmpty()) {
                val gameEvent = value.pop()
                if (gameEvent.gameover) {
                    mediator.dispatch(EndGameCommand(gameEvent.gameover, gameEvent.gameLoss, gameEvent.user))
                }

                if (gameEvent.error != null) {
                    mediator.dispatch(ErrorHandleCommand(gameEvent.error.type, gameEvent.error.message, gameEvent.user))
                } else {
                    sseHandlerImpl.emitTo<GameEventDTO>(key, gameEvent, SseEventType.GAME_EVENT)
                }
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