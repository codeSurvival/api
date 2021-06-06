package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.languages.create_language.CreateLanguageCommand
import com.esgi.codesurvival.application.languages.queries.GetAllLanguagesQuery
import com.esgi.codesurvival.application.languages.queries.LanguageResponseDTO
import com.esgi.codesurvival.application.users.queries.get_user_by_username.GetUserByUsernameQuery
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*


@RestController
@RequestMapping("languages")
class LanguagesController(private val mediator : Mediator) {

    @PostMapping
    fun create(@RequestBody level: CreateLanguageCommand): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(level)
            val uri = MvcUriComponentsBuilder.fromMethodName(LanguagesController::class.java, "getById", created.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID)  {

    }

    @GetMapping("")
    fun getAll() : ResponseEntity<List<LanguageResponseDTO>>  {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetAllLanguagesQuery()))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

    }
}