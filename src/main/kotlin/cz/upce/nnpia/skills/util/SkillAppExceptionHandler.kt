package cz.upce.nnpia.skills.util

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class SkillAppExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(SkillAppException::class)
    fun handleException(ex: SkillAppException, headers: HttpHeaders, status: HttpStatus, request: WebRequest) =
            ResponseEntity<Any>(ErrorResponse(status, ex.message, System.currentTimeMillis()),headers, status)


    override fun handleAsyncRequestTimeoutException(ex: AsyncRequestTimeoutException, headers: HttpHeaders, status: HttpStatus, webRequest: WebRequest): ResponseEntity<Any>? {
        return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest)
    }

}