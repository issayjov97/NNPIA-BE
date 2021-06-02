package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.ChangePasswordRequest
import cz.upce.nnpia.skills.api.ChangeUserInfoRequest
import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.api.toPost
import cz.upce.nnpia.skills.persistence.UserRepository
import cz.upce.nnpia.skills.persistence.WishListEntity
import cz.upce.nnpia.skills.persistence.WishListId
import cz.upce.nnpia.skills.persistence.WishListRepository
import cz.upce.nnpia.skills.service.UserService
import cz.upce.nnpia.skills.util.SkillAppException
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val wishListRepository: WishListRepository
) : UserService {
    @Transactional(readOnly = true)
    override fun getUser(username: String) = userRepository.findByUsername(username)
            ?: throw SkillAppException("User was not found", HttpStatus.NOT_FOUND)

    override fun getWishList(authentication: Authentication): List<Post> {
        val userEntity = userRepository.findByUsername(authentication.principal as String)
        return wishListRepository.findByIdUserId(userEntity?.id!!).map {
            it.post?.toPost()!!
        }
    }

    override fun changePassword(authentication: Authentication, request: ChangePasswordRequest) {
        val bCryptPasswordEncoder = BCryptPasswordEncoder();
        val userEntity = userRepository.findByUsername(authentication.principal as String)
                ?: throw SkillAppException("User was not found", HttpStatus.NOT_FOUND)

        if (!bCryptPasswordEncoder.matches(request.currentPassword, userEntity.password))
            throw SkillAppException("Wrong password", HttpStatus.NOT_FOUND)

        userEntity.password = bCryptPasswordEncoder.encode(request.newPassword)
        userRepository.saveAndFlush(userEntity)
    }

    override fun updateRating(authentication: Authentication, rating: Int) {
        val userEntity = userRepository.findByUsername(authentication.principal as String)
                ?: throw SkillAppException("User not found", HttpStatus.NOT_FOUND)
        userEntity.rating = rating
        userRepository.saveAndFlush(userEntity)
    }

    override fun updateUser(authentication: Authentication, request: ChangeUserInfoRequest) {
        val userEntity = userRepository.findByUsername(authentication.principal as String)
                ?: throw SkillAppException("User not found", HttpStatus.NOT_FOUND)
        request.firstname?.let {
            userEntity.firstname = it
        }
        request.lastname?.let {
            userEntity.lastname = it
        }

        userRepository.saveAndFlush(userEntity)
    }

    override fun addWishPost(authentication: Authentication, postId: Long) {
        userRepository.findByUsername(authentication.principal as String)?.let { user ->
            val wishPost = wishListRepository.findById(
                    WishListId(
                            userId = user.id!!,
                            postId = postId
                    )
            )
            if (wishPost.isPresent)
                throw SkillAppException("Post was already added to your wishlist", HttpStatus.CONFLICT)
            else
                wishListRepository.saveAndFlush(
                        WishListEntity(
                                id = WishListId(
                                        userId = user.id,
                                        postId = postId
                                )
                        )
                )
        }
    }
    //  override fun getHours(userId: Long) =  skillHoursRepository.findByUserId(userId)

}