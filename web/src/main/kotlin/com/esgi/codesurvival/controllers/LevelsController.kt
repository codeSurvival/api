package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.levels.add_constraint.CreateConstraintCommand
import com.esgi.codesurvival.application.levels.create_level.CreateLevelCommand
import com.esgi.codesurvival.dtos.CreateConstraintDTO
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
    @PostMapping("{level_id}/constraints/")
    fun createConstraint(@PathVariable level_id: Int, @RequestBody constraint: CreateConstraintDTO): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(
                    CreateConstraintCommand(level_id, constraint.name, constraint.warning)
            )
            val uri = MvcUriComponentsBuilder.fromMethodName(LevelsController::class.java, "getConstraintById",level_id, created.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("{level_id}/constraints/{constraint_id}")
    fun getConstraintById(@PathVariable level_id: Int, @PathVariable constraint_id: Int)  {

    }
}