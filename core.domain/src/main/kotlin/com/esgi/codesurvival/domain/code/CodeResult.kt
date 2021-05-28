package com.esgi.codesurvival.domain.code

class CodeResult(var success : Boolean, val failedConstraints : MutableList<Constraint>)