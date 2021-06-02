package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.api.AuthenticationRequest
import cz.upce.nnpia.skills.api.SignupRequest
import cz.upce.nnpia.skills.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
        private val authService: AuthService
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request: AuthenticationRequest) = authService.login(request)

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logout(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication
    ) {
        SecurityContextLogoutHandler().logout(request, response, authentication)
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    fun signup(
            @RequestBody request: SignupRequest
    ) = authService.signup(request)
}