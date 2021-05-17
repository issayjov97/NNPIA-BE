package cz.upce.nnpia.skills.util

import org.springframework.http.HttpStatus

class ErrorResponse(
        val status: HttpStatus,
        val message: String?,
        val timestamp: Long
)