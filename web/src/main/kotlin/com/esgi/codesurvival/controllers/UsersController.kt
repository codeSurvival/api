package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.security.parse_token.ParseTokenQuery
import com.esgi.codesurvival.application.users.login.login_user.ConnectedUser
import com.esgi.codesurvival.application.users.login.login_user.LoginUserCommand
import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.queries.get_user_by_id.GetUserByIdQuery
import com.esgi.codesurvival.application.users.login.register_user.UserRegisterCommand
import com.esgi.codesurvival.application.users.queries.get_user_by_username.GetUserByUsernameQuery
import com.esgi.codesurvival.application.users.queries.get_users.GetUsersQuery
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*
import javax.transaction.Transactional

@RestController
@RequestMapping("users")
class UsersController(private val mediator: Mediator) {

    @GetMapping
    @Transactional
    fun getAll(@RequestHeader headers : HttpHeaders) : ResponseEntity<List<UserResume>> {

        return try {
            ResponseEntity.ok(mediator.dispatch(GetUsersQuery()))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @Transactional
    @PostMapping("registration")
    fun register(@RequestBody user: UserRegisterCommand): ResponseEntity<Unit> {
        return try {
            val created = mediator.dispatch(user)
            val uri =
                MvcUriComponentsBuilder.fromMethodName(UsersController::class.java, "getById", created.value.toString())
                    .build().toUri()
            ResponseEntity.created(uri).build()
        } catch (e: ApplicationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @Transactional
    @PostMapping("authentication")
    fun authenticate(@RequestBody credentials: LoginUserCommand): ResponseEntity<ConnectedUser> {
        return try {
            ResponseEntity.ok(mediator.dispatch(credentials))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("me")
    @Transactional
    fun getMe(@RequestHeader headers : HttpHeaders) : ResponseEntity<UserResume> {
        try {
            val token = headers.getFirst(HttpHeaders.AUTHORIZATION)

            token?.let{
                val username = mediator.dispatch(ParseTokenQuery(it))
                return ResponseEntity.ok(mediator.dispatch(GetUserByUsernameQuery(username)))
            }
            return ResponseEntity.badRequest().build()

        } catch (e: ApplicationException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("{id}")
    @Transactional
    fun getById(@PathVariable id: UUID) : ResponseEntity<UserResume> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetUserByIdQuery(id)))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }
}