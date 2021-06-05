package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import com.esgi.codesurvival.infrastructure.models.UserEntity

fun UserEntity.to() = User(UserId(id), username, email, password, role, level)

fun User.to() = UserEntity(id.value, username, email, password, role, level )