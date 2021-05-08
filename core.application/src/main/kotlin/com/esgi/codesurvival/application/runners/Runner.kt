package com.esgi.codesurvival.application.runners

import com.esgi.codesurvival.application.code.execution.CodeOutput
import com.esgi.codesurvival.domain.code.Code

interface Runner {
    fun run(code: Code) : CodeOutput
}