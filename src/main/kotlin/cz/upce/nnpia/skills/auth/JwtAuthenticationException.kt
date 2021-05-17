package cz.upce.nnpia.skills.auth

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(
        val status: HttpStatus,
        msg: String?,
        cause: Throwable?
) : AuthenticationException(msg, cause)