package com.esgi.codesurvival.controllers

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.application.code.compilation.CompilationStepQuery
import com.esgi.codesurvival.application.code.execution.CodeExecutionCommand
import com.esgi.codesurvival.application.code.execution.CodeOutput
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.security.parse_token.ParseTokenQuery
import com.esgi.codesurvival.domain.code.CodeResult
import com.esgi.codesurvival.dtos.CodeExecutionDTO
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional


@RestController
@RequestMapping("code")
class CodeController(private val mediator: Mediator) {

    @Transactional
    @PostMapping("execution")
    fun execute(@RequestHeader headers : HttpHeaders, @RequestBody codeExecutionDTO: CodeExecutionDTO): ResponseEntity<CodeOutput> {
        val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
        val username = mediator.dispatch(ParseTokenQuery(token))
        return try {
            ResponseEntity.ok(mediator.dispatch(CodeExecutionCommand(codeExecutionDTO.code, codeExecutionDTO.language_id, username)))
        }
        catch (e: ApplicationException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Transactional
    @GetMapping("compilationStep")
    fun getCompilationStep(@RequestHeader headers : HttpHeaders): ResponseEntity<CompilationStep?> {
        val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
        val username = mediator.dispatch(ParseTokenQuery(token))
        return try {
            ResponseEntity.ok(mediator.dispatch(CompilationStepQuery(username)))
        }
        catch (e: ApplicationException) {
            ResponseEntity.badRequest().build()
        }
    }
}