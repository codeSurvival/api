package com.esgi.codesurvival.application.code.repositories

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import java.util.*

interface ICompilationStepRepository {
    fun getUserCompilationStep(userId: UUID) : CompilationStep?
    fun add(userId: UUID, step: CompilationStep)
}
