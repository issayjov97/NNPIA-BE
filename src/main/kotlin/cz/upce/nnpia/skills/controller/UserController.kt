package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.api.ChangePasswordRequest
import cz.upce.nnpia.skills.api.ChangeUserInfoRequest
import cz.upce.nnpia.skills.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
class UserController(
        private val userService: UserService
) {

    @PostMapping("/{username}/password")
    fun changePassword(
            @RequestBody request: ChangePasswordRequest,
            authentication: Authentication
    ) = userService.changePassword(authentication, request)

    @PutMapping("/info", consumes = ["multipart/form-data"])
    fun updateUser(
            @RequestPart request: ChangeUserInfoRequest,
            @RequestPart("image") file: MultipartFile,
            authentication: Authentication
    ) = userService.updateUser(authentication, request)


    @PutMapping("/rating")
    fun updateRating(
            @RequestParam rating: Int,
            authentication: Authentication
    ) = userService.updateRating(authentication, rating)

    @PostMapping("/wishlist")
    fun updateWishlist(
            @RequestParam postId: Long,
            authentication: Authentication
    ) = userService.addWishPost(authentication, postId)

    @GetMapping("/wishlist")
    fun getWishlist(
            authentication: Authentication
    ) = userService.getWishList(authentication)
}