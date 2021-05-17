package cz.upce.nnpia.skills.auth

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
        val userDetailsService: SkillsAppUserDetailsService
) {

    companion object {
        private var secretKey = "addai"
    }

    @PostConstruct
    private fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String, roles: List<String>): String {
        val claims = Jwts.claims().setSubject(username)
        roles.takeIf { it.isNotEmpty() }?.apply {
            claims["roles"] = this
        }
        val now = Date()
        val validity = Date(now.time + 3600000)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()

    }

    fun validateToken(token: String): Boolean {
        try {
            return !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration.before(Date())
        } catch (exX: JwtException) {
            throw JwtAuthenticationException(HttpStatus.UNAUTHORIZED,"Token is expired or invalid", null);
        }
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUserName(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUserName(token: String) = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject

    fun resolveToken(request: HttpServletRequest) = request.getHeader("Authorization")
}