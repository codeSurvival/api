package com.esgi.codesurvival.application.users.queries.get_users

import com.esgi.codesurvival.application.users.queries.UserResume
import com.esgi.codesurvival.application.users.repositories.IUsersRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional

class GetUsersQuery(): Request<List<UserResume>>

@Component
@Transactional
open class GetUsersQueryHandler @Autowired constructor(private val repository: IUsersRepository) :
    RequestHandler<GetUsersQuery, List<UserResume>> {
    override fun handle(request: GetUsersQuery): List<UserResume> {
        return repository.findAll().map {
            UserResume(
                it.id.value,
                it.email,
                it.role,
                it.username,
                it.level)
        }
    }
}