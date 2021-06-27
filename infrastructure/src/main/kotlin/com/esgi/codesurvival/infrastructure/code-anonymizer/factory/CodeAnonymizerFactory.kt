package com.esgi.codesurvival.infrastructure.`code-anonymizer`.factory

import com.esgi.codesurvival.application.code.execution.anonymizer.Anonymizer
import com.esgi.codesurvival.application.code.execution.anonymizer.AnonymizerFactory
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.infrastructure.`code-anonymizer`.KotlinAnonymizer
import org.springframework.stereotype.Component


@Component
class CodeAnonymizerFactory : AnonymizerFactory {

    override fun get(language: Language): Anonymizer {
        return when(language.name) {
            "Kotlin" -> KotlinAnonymizer()
            else -> throw ApplicationException("Unhandled language")
        }
    }
}