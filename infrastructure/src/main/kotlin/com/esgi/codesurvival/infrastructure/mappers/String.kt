package com.esgi.codesurvival.infrastructure.mappers

import java.io.File
import java.lang.ProcessBuilder.Redirect.INHERIT
import java.util.concurrent.TimeUnit.MINUTES

fun String.runCommand(workingDir: File) {
    ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(INHERIT)
        .redirectError(INHERIT)
        .start()
        .waitFor(60, MINUTES)
}

fun String.randomValue(len: Int = 15): String{
    val alphanumerics = CharArray(26) { it -> (it + 97).toChar() }.toSet()
        .union(CharArray(9) { it -> (it + 48).toChar() }.toSet())
    return (0 until len).map {
        alphanumerics.toList().random()
    }.joinToString("")
}