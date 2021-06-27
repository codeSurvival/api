package com.esgi.codesurvival.domain.level

import com.esgi.codesurvival.domain.code.Algorithm
import java.util.*

class Constraint(
    val id : UUID,
    val name: String,
    val warning: String,
    val regexes: MutableList<Regex>
) {


}