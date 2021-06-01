package cz.upce.nnpia.skills.util

import org.springframework.http.HttpStatus

class SkillAppException(
        val content: String?,
        val status: HttpStatus,
        val ex: Throwable? = null
) : RuntimeException(content) {
}