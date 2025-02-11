package ru.mai.user.common.config.rest

import com.fasterxml.jackson.annotation.JsonInclude
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import ru.mai.base.user.common.model.error.ApiException

@RestControllerAdvice
class DefaultRestExceptionHandler : Logging {

    @ExceptionHandler(value = [Exception::class])
    fun uncaught(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Uncaught exception in controller: ${e.stackTraceToString()}")
        return ResponseEntity(ErrorResponse(message = "Unable to handle request"), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [ApiException::class])
    fun handle(
        e: ApiException,
        request: ServletWebRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.error("Exception when processing '${request.request.requestURI}': ${e.stackTraceToString()}")
        return with(e.status) {
            ResponseEntity(
                ErrorResponse(code, e.message ?: description),
                httpStatus
            )
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class ErrorResponse(
        val code: Int? = null,
        val message: String,
    )
}
