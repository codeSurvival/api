package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.levels.add_constraint.CreateConstraintCommand
import com.esgi.codesurvival.application.levels.add_constraint.UpdateConstraintCommand
import com.esgi.codesurvival.application.levels.add_regex.CreateRegexCommand
import com.esgi.codesurvival.application.levels.add_regex.UpdateRegexCommand
import com.esgi.codesurvival.application.levels.create_level.CreateLevelCommand
import com.esgi.codesurvival.application.levels.create_level.UpdateLevelCommand
import com.esgi.codesurvival.application.levels.queries.LightLevel
import com.esgi.codesurvival.application.levels.queries.get_level_by_id.GetCompleteLevelByIdQuery
import com.esgi.codesurvival.application.levels.queries.get_level_by_id.GetLevelByIdQuery
import com.esgi.codesurvival.application.levels.queries.get_levels.GetLevelsQuery
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.queries.get_users.GetUsersQuery
import com.esgi.codesurvival.dtos.CreateConstraintDTO
import com.esgi.codesurvival.dtos.CreateRegexDTO
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("levels")
class LevelsController(private val mediator: Mediator) {

    @GetMapping
    fun getAll(@RequestHeader headers : HttpHeaders) : ResponseEntity<List<LightLevel>> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetLevelsQuery()))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping
    fun create(@RequestBody level: CreateLevelCommand): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(level)
            val uri = MvcUriComponentsBuilder.fromMethodName(LevelsController::class.java, "getById", created.toString(), 1).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping("{id}")
    fun update(@PathVariable id: Int, @RequestBody level: CreateLevelCommand): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(UpdateLevelCommand(id, level.turnObjective))
            val uri = MvcUriComponentsBuilder.fromMethodName(LevelsController::class.java, "getById", created.toString(), 1).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Int, @RequestParam complete : Int? ) : ResponseEntity<Any>   {
        return try {
            if( complete == 0 || complete == null) {
                ResponseEntity.ok(mediator.dispatch(GetLevelByIdQuery(id)))
            } else if (complete == 1) {
                ResponseEntity.ok(mediator.dispatch(GetCompleteLevelByIdQuery(id)))
            } else {
                ResponseEntity.badRequest().build()
            }
        } catch(e : ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }


    @PostMapping("{level_id}/constraints")
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

    @PostMapping("{level_id}/constraints/{constraint_id}")
    fun update(@PathVariable level_id: Int, @PathVariable constraint_id: UUID, @RequestBody constraint: CreateConstraintDTO): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(
                UpdateConstraintCommand(level_id, constraint_id,  constraint.name, constraint.warning)
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

    @PostMapping("{level_id}/constraints/{constraint_id}/regex")
    fun createRegex(@PathVariable level_id: Int, @PathVariable constraint_id: UUID, @RequestBody regex: CreateRegexDTO): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(
                CreateRegexCommand(constraint_id, regex.pattern, regex.language_id)
            )
            val uri = MvcUriComponentsBuilder.fromMethodName(LevelsController::class.java, "getRegexById",level_id, created.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping("{level_id}/constraints/{constraint_id}/regex/{regex_id}")
    fun createRegex(@PathVariable level_id: Int, @PathVariable constraint_id: UUID, @PathVariable regex_id: UUID, @RequestBody regex: CreateRegexDTO): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(
                UpdateRegexCommand(constraint_id, regex_id, regex.pattern, regex.language_id)
            )
            val uri = MvcUriComponentsBuilder.fromMethodName(LevelsController::class.java, "getRegexById",level_id, created.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("{level_id}/constraints/{constraint_id}/regex/{regex_id}")
    fun getRegexById(@PathVariable level_id: Int, @PathVariable constraint_id: Int)  {

    }
}