package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.levels.create_level.CreateLevelCommand
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder

@RestController
@RequestMapping("levels")
class LevelsController(private val mediator: Mediator) {

    @PostMapping
    fun create(@RequestBody level: CreateLevelCommand): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(level)
            val uri = MvcUriComponentsBuilder.fromMethodName(LevelsController::class.java, "getById", created.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Int)  {

    }
}