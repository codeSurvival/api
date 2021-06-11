package com.esgi.codesurvival.application.code.execution.compilator

import com.esgi.codesurvival.domain.level.Language

interface CompilatorFactory {
    fun get(language: Language): Compilator
}