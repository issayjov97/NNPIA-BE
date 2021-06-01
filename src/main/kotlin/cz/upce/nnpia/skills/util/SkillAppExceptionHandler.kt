package cz.upce.nnpia.skills.util

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class SkillAppExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val LOGGER: Logger = LogManager.getLogger(SkillAppExceptionHandler::class.java)
    }

    @ExceptionHandler(SkillAppException::class)
    fun handleException(ex: SkillAppException, request: WebRequest): ResponseEntity<Any> {
        LOGGER.error(ex)
        return buildErrorResponse(
                ex,
                ex.status,
                request
        )
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return buildErrorResponse(
                ex,
                HttpStatus.BAD_REQUEST,
                request,
                ex.bindingResult.fieldErrors.map {
                    ValidationError(
                            message = it.defaultMessage,
                            filed = it.field
                    )
                }
        )
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, ex, headers, status, request)
    }

    override fun handleExceptionInternal(ex: Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return buildErrorResponse(ex, status, request)

    }

    private fun buildErrorResponse(
            ex: Exception,
            status: HttpStatus,
            request: WebRequest,
            errors: List<ValidationError>? = null
    ): ResponseEntity<Any> {
        return ResponseEntity<Any>(
                ErrorResponse(
                        message = ex.message,
                        status = status.value(),
                        timestamp = System.currentTimeMillis(),
                        errors = errors
                ),
                status
        )
    }
}

data class ErrorResponse(
        val message: String?,
        val status: Int,
        val timestamp: Long,
        val errors: List<ValidationError>? = null
)

data class ValidationError(
        val filed: String,
        val message: String?
)