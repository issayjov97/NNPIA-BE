package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.api.ChangePasswordRequest
import cz.upce.nnpia.skills.service.UserService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
class UserController(
        private val userService: UserService
) {

    @PostMapping("/{userName}/password")
    fun changePassword(
            @PathVariable userName: String,
            @RequestBody request: ChangePasswordRequest
    ) {
        userService.changePassword(userName, request)
    }
}