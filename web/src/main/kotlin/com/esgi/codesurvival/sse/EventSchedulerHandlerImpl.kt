package com.esgi.codesurvival.sse

import com.esgi.codesurvival.application.sse.EventSchedulerHandler
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.sse.jackets.SseEventType
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
@EnableScheduling
class EventSchedulerHandlerImpl(val sseHandlerImpl: SseHandler): EventSchedulerHandler {

    val gameEventsByUserId: HashMap<String, Deque<String>> = HashMap()
    val dequeToRemove : MutableList<String> = mutableListOf()

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
                sseHandlerImpl.emitTo(key, value.pop(), SseEventType.GAME_EVENT)
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