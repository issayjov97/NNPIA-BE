package cz.upce.nnpia.skills.auth

import cz.upce.nnpia.skills.service.impl.UserServiceImpl
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SkillsAppUserDetailsService(
        val userService: UserServiceImpl
) : UserDetailsService {
    override fun loadUserByUsername(userName: String): UserDetails {
        val userEntity = userService.getUser(userName)
        return User(
                userEntity.email,
                userEntity.password,
                userEntity.authorities.map { SimpleGrantedAuthority(it.authority) }
        )
    }

}
