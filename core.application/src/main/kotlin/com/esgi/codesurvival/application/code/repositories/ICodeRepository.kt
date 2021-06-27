package com.esgi.codesurvival.application.code.repositories

import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.user.UserId
import java.util.*

interface ICodeRepository {

    fun save(code: Algorithm): UUID
}