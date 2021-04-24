package com.esgi.codesurvival.application.users.queries

import com.esgi.codesurvival.domain.user.Role
import java.util.*

data class UserResume(
    val id: UUID,
    val email: String,
    val role: Role,
    val username: String)