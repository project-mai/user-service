package ru.mai.base.user.common.model.error

import org.springframework.http.HttpStatus
import ru.mai.base.user.common.model.error.ApiResponseStatus.UNKNOWN_INTERNAL_ERROR

class ApiExceptionErrorDetails(
    var code: Int,
    var description: String? = null,
)

open class ApiException(
    val status: ApiResponseStatus,
    val errors: List<ApiExceptionErrorDetails>?,
) : RuntimeException(errors?.firstOrNull()?.description) {
    constructor(
        status: ApiResponseStatus,
        vararg args: Any?,
    ) : this(
        status,
        listOf(ApiExceptionErrorDetails(status.code, String.format(status.description, *args))),
    )

    constructor(
        errors: List<ApiExceptionErrorDetails>?,
    ) : this(
        UNKNOWN_INTERNAL_ERROR,
        errors
    )
}

enum class ApiResponseStatus(
    val code: Int,
    val description: String,
    val httpStatus: HttpStatus,
) {
    UNKNOWN_INTERNAL_ERROR(1000, "Unknown internal error", HttpStatus.UNPROCESSABLE_ENTITY),
}
