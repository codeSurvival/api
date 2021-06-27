package com.esgi.codesurvival.application.code.repositories

import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.user.UserId

interface ICodeOwnerRepository {

    fun getUserPreviousCode(userId: UserId) : Algorithm?
}