package com.esgi.codesurvival.domain.code

import com.esgi.codesurvival.domain.level.Constraint

class CodeResult(var success : Boolean, val failedConstraints : MutableList<Constraint>)