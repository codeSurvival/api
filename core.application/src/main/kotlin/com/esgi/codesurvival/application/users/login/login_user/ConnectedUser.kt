package com.esgi.codesurvival.application.users.login.login_user

import com.esgi.codesurvival.domain.user.Role
import java.util.*

data class ConnectedUser(
    val id: UUID,
    val email: String,
    val role: Role,
    val username: String,
    val token: String)
