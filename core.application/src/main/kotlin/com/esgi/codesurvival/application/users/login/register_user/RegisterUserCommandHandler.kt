package com.esgi.codesurvival.application.users.login.register_user;

import com.esgi.codesurvival.application.contracts.services.IHashingService
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.User
import com.esgi.codesurvival.domain.user.UserId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

data class UserRegisterCommand(var username: String, var password: String, var email: String) : Request<UserId>

@Component
class RegisterUserCommandHandler @Autowired constructor(
    private val repository: IUsersRepository,
    private val hashingService: IHashingService
) :
    RequestHandler<UserRegisterCommand, UserId> {
    override fun handle(request: UserRegisterCommand): UserId {
        val existingAccounts = repository.findAll()

        val newAccount = User(
            id = repository.getNewId(),
            username = request.username,
            email = request.email,
            password = hashingService.hashPassword(request.password),
            level = 1
        )

        newAccount.setRole(existingAccounts)

        if (!newAccount.isUnique(existingAccounts)) {
            throw ApplicationException("conflict")
        }

        return repository.save(newAccount)
    }
}
