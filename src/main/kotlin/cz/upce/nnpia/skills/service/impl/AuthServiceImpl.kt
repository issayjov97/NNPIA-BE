package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.AuthenticationRequest
import cz.upce.nnpia.skills.api.SignupRequest
import cz.upce.nnpia.skills.api.User
import cz.upce.nnpia.skills.auth.JwtTokenProvider
import cz.upce.nnpia.skills.persistence.SkillHoursEntity
import cz.upce.nnpia.skills.persistence.UserEntity
import cz.upce.nnpia.skills.persistence.UserRepository
import cz.upce.nnpia.skills.service.AuthService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
        val userRepository: UserRepository,
        val authenticationManager: AuthenticationManager,
        val jwtTokenProvider: JwtTokenProvider,
        val passwordEncoder: PasswordEncoder
) : AuthService {
    @Transactional(readOnly = true)
    override fun login(request: AuthenticationRequest): User {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
                ?: throw SkillAppException("Invalid email or password")
        val userEntity = userRepository.findByUsername(request.email) ?: throw SkillAppException("User not found")
        val token = jwtTokenProvider.createToken(userEntity.username, mutableListOf())
        return User(
                username = userEntity.email,
                token = token,
                firstname = userEntity.firstname,
                lastname = userEntity.lastname,
                rating = userEntity.rating,
                earned = userEntity.skillHoursEntity.earned,
                available = userEntity.skillHoursEntity.available,
                used = userEntity.skillHoursEntity.used,
                authorities = userEntity.authorities.map { it.authority }
        )
    }

    @Transactional
    override fun signup(request: SignupRequest) {
        val userEntity = userRepository.findByUsername(request.username)
        userEntity?.let {
            throw SkillAppException("Username already exists")
        }
        userRepository.save(
                UserEntity(
                        username = request.username,
                        password = passwordEncoder.encode(request.password),
                        firstname = request.firstname,
                        lastname = request.lastname,
                        email = request.email,
                        skillHoursEntity = SkillHoursEntity()
                )
        )
    }


}