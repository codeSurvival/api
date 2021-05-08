package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.code.execution.CodeExecutionCommand
import com.esgi.codesurvival.application.code.execution.CodeOutput
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.queries.get_user_by_id.GetUserByIdQuery
import com.esgi.codesurvival.dtos.CodeExecutionDTO
import io.jkratz.mediator.core.Mediator
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("code")
class CodeController(private val mediator: Mediator) {

    @PostMapping("execution")
    fun execute(@RequestBody codeExecutionDTO: CodeExecutionDTO, authentication: Authentication): ResponseEntity<CodeOutput> {
        var username = authentication.principal as String

        return try {
            ResponseEntity.ok(mediator.dispatch(CodeExecutionCommand(codeExecutionDTO.code, codeExecutionDTO.language, username)))
        }
        catch (e: ApplicationException) {
            ResponseEntity.badRequest().build()
        }
    }
}