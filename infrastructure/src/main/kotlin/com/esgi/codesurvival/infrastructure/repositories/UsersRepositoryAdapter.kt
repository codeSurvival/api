package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import com.esgi.codesurvival.infrastructure.mappers.to
import com.esgi.codesurvival.infrastructure.models.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class UsersRepositoryAdapter @Autowired constructor(private val repository: UsersRepository) : IUsersRepository {
    override fun findAll(): List<User> {
        return repository.findAll().map { it.to() }
    }

    override fun save(user: User): UserId {
        return UserId(
            repository.save(UserEntity(
                id = user.id.value,
                username = user.username,
                email = user.email,
                password = user.password,
                role = user.role,
                level = user.level
            ))
                .id
        )
    }

    override fun getNewId(): UserId {
        return UserId(UUID.randomUUID())
    }

    override fun findByCredentials(username: String, password: String): User? {
        return repository.findByUsernameAndPassword(username, password)?.to()
    }

    override fun findByUsername(username: String): User? {
        return repository.findByUsername(username)?.to()
    }

    override fun findById(id: UserId): User? {
        return repository.findByIdOrNull(id.value)?.to()
    }
}