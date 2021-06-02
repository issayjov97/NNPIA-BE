package cz.upce.nnpia.skills

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import java.util.*


@TestConfiguration
class SpringSecurityTestConfig {

    @Bean
    @Primary
    fun userDetailsService(): UserDetailsService? {
        val userTest = User(
                "testt@test.cz",
                "password",
                listOf(
                        SimpleGrantedAuthority("ADMIN")
                )
        )
        return InMemoryUserDetailsManager(listOf(
                userTest
        ))
    }
}