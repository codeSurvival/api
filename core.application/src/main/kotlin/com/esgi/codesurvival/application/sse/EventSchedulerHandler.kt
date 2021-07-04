package com.esgi.codesurvival.application.sse

interface EventSchedulerHandler {
    fun addEvent(userId: String, gameEventString: String)
}