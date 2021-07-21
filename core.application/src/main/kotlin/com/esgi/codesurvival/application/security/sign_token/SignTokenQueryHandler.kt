package com.esgi.codesurvival.application.security.sign_token

import com.esgi.codesurvival.application.contracts.services.ITokenService
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional


data class SignTokenQuery(val input : String ) : Request<String>


@Component
@Transactional
open class SignTokenQueryHandler @Autowired constructor(private val tokenService: ITokenService) :
    RequestHandler<SignTokenQuery, String> {
    override fun handle(request: SignTokenQuery): String {
        return tokenService.sign(request.input)
    }
}