package com.esgi.codesurvival.application.users.queries.get_user_by_username

import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


data class GetUserByUsernameQuery(val username : String): Request<UserResume>

@Component
class GetUserByUsernameQueryHandler @Autowired constructor(private val repository: IUsersRepository) :
    RequestHandler<GetUserByUsernameQuery, UserResume> {
    override fun handle(request: GetUserByUsernameQuery): UserResume {
        repository.findByUsername(request.username)?.let {
            return UserResume(
                it.id.value,
                it.email,
                it.role,
                it.username,
                it.level)
        }

        throw ApplicationException("User not found")
    }
}