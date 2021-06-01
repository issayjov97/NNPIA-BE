package cz.upce.nnpia.skills.auth

import cz.upce.nnpia.skills.persistence.SkillHoursEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SkillsAppUser(
        private val username: String,
        private val password: String,
        private val enabled: Boolean,
        private val authorities: Set<GrantedAuthority>,
        val firstname: String,
        val lastname: String,
        val email: String,
        val rating: Int,
        val skillHoursEntity: SkillHoursEntity

) : UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled
}