package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.AuthenticationRequest
import cz.upce.nnpia.skills.api.SignupRequest
import cz.upce.nnpia.skills.api.User
import cz.upce.nnpia.skills.auth.JwtTokenProvider
import cz.upce.nnpia.skills.auth.SkillsAppUser
import cz.upce.nnpia.skills.persistence.SkillHoursEntity
import cz.upce.nnpia.skills.persistence.UserEntity
import cz.upce.nnpia.skills.persistence.UserRepository
import cz.upce.nnpia.skills.service.AuthService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.http.HttpStatus
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
        val skillsAppUser = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
        ).principal as SkillsAppUser

        val token = jwtTokenProvider.createToken(
                username = skillsAppUser.username,
                roles = skillsAppUser.authorities.map {
                    it.authority
                }.toSet()
        )
        return User(
                username = skillsAppUser.email,
                token = token,
                firstname = skillsAppUser.firstname,
                lastname = skillsAppUser.lastname,
                rating = skillsAppUser.rating,
                earned = skillsAppUser.skillHoursEntity.earned,
                available = skillsAppUser.skillHoursEntity.available,
                used = skillsAppUser.skillHoursEntity.used,
                authorities = skillsAppUser.authorities.map { it.authority }
        )
    }

    @Transactional
    override fun signup(request: SignupRequest) {
        val userEntity = userRepository.findByUsername(request.username)
        userEntity?.let {
            throw SkillAppException("Username already exists", HttpStatus.BAD_REQUEST)
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