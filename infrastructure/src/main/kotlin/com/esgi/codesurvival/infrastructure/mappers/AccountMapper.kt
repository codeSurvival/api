package com.esgi.codesurvival.infrastructure.mappers

import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import com.esgi.codesurvival.infrastructure.models.UserEntity
import java.util.*

fun UserEntity.to() = User(
    id = UserId(value = id),
    username = username,
    email = email,
    password = password,
    role = role,
    level = level,
    lastCodeId = lastCodeId
)

fun User.to(lastCodeId: UUID?) = UserEntity(
    id = id.value,
    username = username,
    email = email,
    password = password,
    role = role,
    level = level,
    lastCodeId = lastCodeId
)