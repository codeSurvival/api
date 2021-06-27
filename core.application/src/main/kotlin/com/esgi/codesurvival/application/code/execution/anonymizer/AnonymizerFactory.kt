package com.esgi.codesurvival.application.code.execution.anonymizer

import com.esgi.codesurvival.domain.level.Language

interface AnonymizerFactory {
    fun get(language: Language): Anonymizer
}