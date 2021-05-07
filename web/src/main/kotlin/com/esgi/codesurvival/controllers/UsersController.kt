package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.login.login_user.ConnectedUser
import com.esgi.codesurvival.application.users.login.login_user.LoginUserCommand
import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.queries.get_user_by_id.GetUserByIdQuery
import com.esgi.codesurvival.application.users.login.register_user.UserRegisterCommand
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("users")
class UsersController(private val mediator: Mediator) {

    @GetMapping
    fun get(): Boolean {
        return true
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID) : ResponseEntity<UserResume> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetUserByIdQuery(id)))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("registration")
    fun register(@RequestBody user: UserRegisterCommand): ResponseEntity<Unit> {
        return try {
            val created = mediator.dispatch(user)
            val uri =
                MvcUriComponentsBuilder.fromMethodName(UsersController::class.java, "getById", created.value.toString())
                    .build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: ApplicationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PostMapping("authentication")
    fun authenticate(@RequestBody credentials: LoginUserCommand): ResponseEntity<ConnectedUser> {
        return try {
            ResponseEntity.ok(mediator.dispatch(credentials))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }
}