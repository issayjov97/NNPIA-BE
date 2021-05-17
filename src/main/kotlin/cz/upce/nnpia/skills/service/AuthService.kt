package cz.upce.nnpia.skills.service

import cz.upce.nnpia.skills.api.AuthenticationRequest
import cz.upce.nnpia.skills.api.User
import cz.upce.nnpia.skills.api.SignupRequest

interface AuthService {
    fun login(request: AuthenticationRequest): User
    fun signup(request: SignupRequest)
}