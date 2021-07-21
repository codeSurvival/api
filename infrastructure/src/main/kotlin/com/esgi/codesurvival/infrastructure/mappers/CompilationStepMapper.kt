package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.application.code.compilation.CompilationStep
import com.esgi.codesurvival.infrastructure.models.CompilationStepEntity

fun CompilationStep.to() = CompilationStepEntity(
    compilationType,
)

fun CompilationStepEntity.to() = CompilationStep(
    stepEntity
)