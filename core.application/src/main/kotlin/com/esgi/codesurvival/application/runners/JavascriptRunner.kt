package com.esgi.codesurvival.application.runners

import com.esgi.codesurvival.application.code.execution.CodeOutput
import com.esgi.codesurvival.domain.code.Code
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class JavascriptRunner : Runner {
    override fun run(code: Code): CodeOutput {
        var output = CodeOutput("", "")

        val scriptEngineManager = ScriptEngineManager()
        val engine = scriptEngineManager.getEngineByName("js")
        try {
            engine.eval(code.toString())
            val returnValue = engine["output"] as Int
            println("output: $returnValue")
            output.returnValue = "output: $returnValue"

            return output
        } catch (scriptException: ScriptException) {
            println("ERROR SCRIPT EXCEPTION")
            println(scriptException.message)

            output.errorValue = scriptException.message.toString()
            return output
        }
    }
}