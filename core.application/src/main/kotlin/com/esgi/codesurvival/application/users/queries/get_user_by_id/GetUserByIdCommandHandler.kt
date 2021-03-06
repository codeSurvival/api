package com.esgi.codesurvival.application.users.queries.get_user_by_id

import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.UserId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

data class GetUserByIdQuery(val id: UUID) : Request<UserResume>

@Component
open class GetUserByIdCommandHandler(private val repository: IUsersRepository) :
    RequestHandler<GetUserByIdQuery, UserResume> {
    @Transactional
    override fun handle(request: GetUserByIdQuery): UserResume {
        (this.repository.findById(UserId(request.id)))?.let {
            return UserResume(
                id = it.id.value,
                username = it.username,
                email = it.email,
                role = it.role,
                level = it.level
            )
        }

        throw ApplicationException("User not found")

    }

}

