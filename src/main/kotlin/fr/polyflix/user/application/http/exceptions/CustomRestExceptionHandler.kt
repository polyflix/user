package fr.polyflix.user.application.http.exceptions

import fr.polyflix.user.application.http.dto.response.ApiError
import fr.polyflix.user.domain.error.UserHasNoSufficientPermissionsError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class CustomRestExceptionHandler: ResponseEntityExceptionHandler() {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        logger.error("${ex.message}")

        val status = when(ex) {
            is UserHasNoSufficientPermissionsError -> HttpStatus.FORBIDDEN
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val error = ApiError(
            status,
            ex.message ?: "No message for this error",
            ex.javaClass.canonicalName
        )

        return ResponseEntity<Any>(error, HttpHeaders(), error.status)
    }
}