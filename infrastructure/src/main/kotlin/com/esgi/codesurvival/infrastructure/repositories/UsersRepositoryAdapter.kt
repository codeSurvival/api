package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.code.repositories.ICodeOwnerRepository
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import com.esgi.codesurvival.infrastructure.mappers.to
import com.esgi.codesurvival.infrastructure.models.CodeEntity
import com.esgi.codesurvival.infrastructure.models.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
@Transactional
open class UsersRepositoryAdapter @Autowired constructor(
    private val repository: UsersRepository,
    private val codeRepository: CodeRepository
    ) : IUsersRepository, ICodeOwnerRepository {
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
                level = user.level,
                lastCodeId = user.lastCodeId
            ))
                .id
        )
    }

    override fun getNewId(): UserId {
        return UserId(UUID.randomUUID())
    }

    @Transactional
    override fun findByCredentials(username: String, password: String): User? {
        return repository.findByUsernameAndPassword(username, password)?.to()
    }

    override fun findByUsername(username: String): User? {
        return repository.findByUsername(username)?.to()
    }

    override fun findById(id: UserId): User? {
        return repository.findByIdOrNull(id.value)?.to()
    }

    override fun getUserPreviousCode(userId: UserId): Algorithm? {
        val savedUser = repository.findByIdOrNull(userId.value)?.to() ?: return null
        if (savedUser.lastCodeId == null) {
            return null
        }
        return codeRepository.findByIdOrNull(savedUser.lastCodeId)?.to()
    }
}