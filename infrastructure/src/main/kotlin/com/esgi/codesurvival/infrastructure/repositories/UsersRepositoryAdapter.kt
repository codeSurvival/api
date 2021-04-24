package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@Service
class UsersRepositoryAdapter  @Autowired constructor(private val repository: UsersRepository) : IUsersRepository  {
    override fun findAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun save(account: User): UserId {
        TODO("Not yet implemented")
    }

    override fun getNewId(): UserId {
        TODO("Not yet implemented")
    }

    override fun findByCredentials(username: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override fun findByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override fun findById(id: UserId): User? {
        TODO("Not yet implemented")
    }
}