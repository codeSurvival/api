package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.sse.SseHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter


@RestController
@RequestMapping("gameEvents")
class SseController(
    val sseHandler: SseHandler
    ) {

    @GetMapping ("{uid}")
    fun createConnection(@PathVariable("uid") uid: String): SseEmitter? {
        return sseHandler.subscribeToSse(uid)
    }
}