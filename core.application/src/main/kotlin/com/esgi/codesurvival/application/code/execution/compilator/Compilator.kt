package com.esgi.codesurvival.application.code.execution.compilator

interface Compilator {
    fun compileAndExecute(compilatorPaths: CompilatorPaths)
    fun buildEntrypoint(): CompilatorPaths
    fun clean(compilatorPaths: CompilatorPaths)
}