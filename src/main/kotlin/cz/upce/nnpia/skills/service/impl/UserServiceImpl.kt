package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.ChangePasswordRequest
import cz.upce.nnpia.skills.persistence.SkillHoursEntity
import cz.upce.nnpia.skills.persistence.SkillHoursRepository
import cz.upce.nnpia.skills.persistence.UserRepository
import cz.upce.nnpia.skills.persistence.WishListEntity
import cz.upce.nnpia.skills.service.UserService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
        private val userRepository: UserRepository
) : UserService {
    @Transactional(readOnly = true)
    override fun getUser(username: String) = userRepository.findByUsername(username)
            ?: throw SkillAppException("User not found")

    override fun getWishList(username: String): List<WishListEntity> {
        TODO("Not yet implemented")
    }

    override fun addWish(username: String) {
        TODO("Not yet implemented")
    }

    override fun changePassword(userName: String, request: ChangePasswordRequest) {
        val bCryptPasswordEncoder = BCryptPasswordEncoder();
        val userEntity = userRepository.findByEmail(userName)
                ?: throw SkillAppException("User not found")

        if (!bCryptPasswordEncoder.matches(request.currentPassword, userEntity.password))
            throw SkillAppException("Wrong user current password")

        userEntity.password = bCryptPasswordEncoder.encode(request.newPassword)
        userRepository.saveAndFlush(userEntity)

    }
    //  override fun getHours(userId: Long) =  skillHoursRepository.findByUserId(userId)

}