package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.security.parse_token.ParseTokenQuery
import com.esgi.codesurvival.application.sse.SseHandler
import com.esgi.codesurvival.application.users.queries.get_user_by_username.GetUserByUsernameQuery
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter


@RestController
@RequestMapping("gameEvents")
class GameEventController(
    val sseHandler: SseHandler,
    private val mediator: Mediator
) {

    @GetMapping(path = ["/sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun createConnection(@RequestHeader headers : HttpHeaders): SseEmitter? {
        val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
        val username = mediator.dispatch(ParseTokenQuery(token))
        val user = mediator.dispatch(GetUserByUsernameQuery(username))
        return sseHandler.subscribeToGameEventListening(user.id.toString())
    }

}