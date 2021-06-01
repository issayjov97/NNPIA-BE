package cz.upce.nnpia.skills.auth

import cz.upce.nnpia.skills.service.impl.UserServiceImpl
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SkillsAppUserDetailsService(
        val userService: UserServiceImpl
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userService.getUser(username)
        return SkillsAppUser(
                username = userEntity.username,
                password = userEntity.password,
                authorities = userEntity.authorities.map {
                    SimpleGrantedAuthority(it.authority)
                }.toSet(),
                enabled = userEntity.enabled,
                firstname = userEntity.firstname,
                lastname = userEntity.lastname,
                email = userEntity.email,
                skillHoursEntity = userEntity.skillHoursEntity,
                rating = userEntity.rating
        )
    }
}
