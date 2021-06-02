package cz.upce.nnpia.skills.service

import cz.upce.nnpia.skills.api.ChangePasswordRequest
import cz.upce.nnpia.skills.api.ChangeUserInfoRequest
import cz.upce.nnpia.skills.api.Post
import cz.upce.nnpia.skills.persistence.UserEntity
import cz.upce.nnpia.skills.persistence.WishListEntity
import org.springframework.security.core.Authentication

interface UserService {
    fun getUser(username: String): UserEntity
    fun getWishList(authentication: Authentication): List<Post>
    fun changePassword(authentication: Authentication, request: ChangePasswordRequest)
    fun updateRating(authentication: Authentication, rating: Int)
    fun updateUser(authentication: Authentication, request: ChangeUserInfoRequest)
    fun addWishPost(authentication: Authentication, postId: Long)
}