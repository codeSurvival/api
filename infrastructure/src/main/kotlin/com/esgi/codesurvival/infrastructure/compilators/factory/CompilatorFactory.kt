package com.esgi.codesurvival.infrastructure.compilators.factory

import com.esgi.codesurvival.application.code.execution.compilator.Compilator
import com.esgi.codesurvival.application.code.execution.compilator.CompilatorFactory
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.infrastructure.compilators.KotlinCompilator
import org.springframework.stereotype.Component

@Component
class CompilatorFactoryImplem: CompilatorFactory {

    override fun get(language: Language): Compilator {
        return when(language.name) {
            "Kotlin" -> KotlinCompilator()
            else -> throw ApplicationException("Unhandled language")
        }
    }
}