package com.esgi.codesurvival.domain.level

import com.esgi.codesurvival.domain.code.Algorithm
import java.util.*

class Constraint(
    val id : UUID,
    var name: String,
    var warning: String,
    val regexes: MutableList<Regex>
) {


}