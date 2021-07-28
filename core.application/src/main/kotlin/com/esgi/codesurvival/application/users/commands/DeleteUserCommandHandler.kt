package com.esgi.codesurvival.application.users.commands

import com.esgi.codesurvival.application.contracts.services.IHashingService
import com.esgi.codesurvival.application.contracts.services.ITokenService
import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.UserId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

data class DeleteUserCommand(val id: UUID) : Request<Unit>

@Component
@Transactional
open class DeleteUserCommandHandler(private val repository: IUsersRepository,
) : RequestHandler<DeleteUserCommand, Unit> {
    override fun handle(request: DeleteUserCommand) {
        val user = repository.findById(UserId(request.id)) ?: throw ApplicationException("No user")
        repository.deleteById(user)
    }

}