package com.esgi.codesurvival.application.security.parse_token

import com.esgi.codesurvival.application.contracts.services.ITokenService
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

data class ParseTokenQuery(val token : String ) : Request<String>

@Component
class ParseTokenQueryHandler @Autowired constructor(private val tokenService: ITokenService) :
    RequestHandler<ParseTokenQuery, String> {
    override fun handle(request: ParseTokenQuery): String {
        return tokenService.parse(request.token)
    }
}
