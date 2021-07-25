package com.esgi.codesurvival

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableTransactionManagement
class CodeSurvivalApplication
fun main(args: Array<String>) {
    runApplication<CodeSurvivalApplication>(*args)
}
