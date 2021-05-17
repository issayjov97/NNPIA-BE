package cz.upce.nnpia.skills.service

import cz.upce.nnpia.skills.api.ChangePasswordRequest
import cz.upce.nnpia.skills.persistence.UserEntity
import cz.upce.nnpia.skills.persistence.WishListEntity

interface UserService {
    fun getUser(username: String): UserEntity
    fun getWishList(username :String): List<WishListEntity>
    fun addWish(username :String)
    fun changePassword(username: String, request: ChangePasswordRequest)
}