package com.esgi.codesurvival

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
class CodeSurvivalApplication
fun main(args: Array<String>) {
    runApplication<CodeSurvivalApplication>(*args)
}
