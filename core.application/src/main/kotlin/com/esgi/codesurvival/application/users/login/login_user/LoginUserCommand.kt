package com.esgi.codesurvival.application.users.login.login_user

import com.esgi.codesurvival.application.contracts.services.IHashingService
import com.esgi.codesurvival.application.contracts.services.ITokenService
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component


class LoginUserCommand(var username: String, var password: String) : Request<ConnectedUser>

@Component
class LoginUserCommandHandler(private val repository: IUsersRepository,
                       private val tokenService: ITokenService,
                       private val securityService: IHashingService
) : RequestHandler<LoginUserCommand, ConnectedUser> {

    override fun handle(request: LoginUserCommand): ConnectedUser {
        val user = repository.findByCredentials(request.username, securityService.hashPassword(request.password))

        user?.let {
            return ConnectedUser(
                id = user.id.value,
                email = user.email,
                role = user.role,
                username = user.username,
                token = tokenService.sign(user.username)
            )
        }

        throw ApplicationException("user not found")
    }

}

