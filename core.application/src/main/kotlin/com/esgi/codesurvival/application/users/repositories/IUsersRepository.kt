package com.esgi.codesurvival.application.users.repositories

import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import java.util.*

interface IUsersRepository {

    fun findAll(): List<User>

    fun save(user: User): UserId
    fun getNewId(): UserId
    fun findByCredentials(username: String, password: String): User?

    fun findByUsername(username: String): User?

    fun findById(id : UserId): User?
    fun deleteById(user: User)
}