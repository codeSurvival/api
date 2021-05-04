package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.infrastructure.models.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsersRepository : JpaRepository<UserEntity, UUID> {

    fun findByUsernameAndPassword(username: String, password: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
}