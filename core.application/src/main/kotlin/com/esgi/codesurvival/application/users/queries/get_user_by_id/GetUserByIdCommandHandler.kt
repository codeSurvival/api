package com.esgi.codesurvival.application.users.queries.get_user_by_id

import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import com.esgi.codesurvival.domain.user.UserId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

data class GetUserByIdQuery(val id: UUID) : Request<UserResume>

@Component
class GetUserByIdCommandHandler(private val repository: IUsersRepository) :
    RequestHandler<GetUserByIdQuery, UserResume> {
    override fun handle(request: GetUserByIdQuery): UserResume {
        (this.repository.findById(UserId(request.id)))?.let {
            return UserResume(
                id = it.id.value,
                username = it.username,
                email = it.email,
                role = it.role,
            )
        }

        throw ApplicationException("User not found")

    }

}

