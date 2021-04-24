package com.esgi.codesurvival.security


import com.esgi.codesurvival.application.security.ApplicationException
import com.esgi.codesurvival.application.users.queries.get_user_by_username.GetUserByUsernameQuery
import com.esgi.codesurvival.domain.user.Role
import io.jkratz.mediator.core.Mediator
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service


@Service
class AuthTokenSecurityProvider(private val mediator: Mediator) : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(auth: Authentication?): Authentication? {
        if (auth == null) {
            return null
        }
        val name: String = auth.name
        if (name.isEmpty()) {
            return null
        }
        val grantedAuths: MutableList<GrantedAuthority> = ArrayList()
        try {
            val userResume = mediator.dispatch(GetUserByUsernameQuery(name))
            userResume.let {
                grantedAuths.clear()
                when (userResume.role) {
                    Role.USER -> grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
                    Role.ADMIN -> grantedAuths.add(SimpleGrantedAuthority("ROLE_ADMIN"))
                }
                val retVal = UsernamePasswordAuthenticationToken(
                    name, "UserAuthenticated", grantedAuths
                )
                println("Add Auth - User Name: " + retVal.name)
                println("Add Auth - Roles Count: " + retVal.authorities?.size ?: 0)
                return retVal
            }
            return null
        } catch (e: ApplicationException) {
            return null
        }
    }

    override fun supports(tokenClass: Class<*>): Boolean {
        return tokenClass == UsernamePasswordAuthenticationToken::class.java
    }
}