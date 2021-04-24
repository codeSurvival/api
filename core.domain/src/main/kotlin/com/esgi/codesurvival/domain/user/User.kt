package com.esgi.codesurvival.domain.user

class User (
    val id: UserId,
    val username: String,
    val email: String,
    val password: String,
    var role: Role = Role.USER
)