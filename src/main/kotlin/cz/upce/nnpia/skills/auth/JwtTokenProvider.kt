package cz.upce.nnpia.skills.auth

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
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

    fun createToken(username: String, roles: Set<String>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = roles
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
            throw JwtAuthenticationException(HttpStatus.UNAUTHORIZED, "Token is expired or invalid", null);
        }
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
        val authorities = (claims["roles"]as List<*>).map { SimpleGrantedAuthority(it as String) }
        return UsernamePasswordAuthenticationToken(claims.subject, "", authorities)
    }

    fun resolveToken(request: HttpServletRequest) = request.getHeader("Authorization")
}