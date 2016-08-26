package me.murooka.taskit.exception

open class ResponseException(val status: Int, message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class NotFoundException(
        message: String = "Not found",
        cause: Throwable? = null
) : ResponseException(status = 404, message = message, cause = cause)

class InvalidParamException(
        message: String = "Invalid parameter",
        cause: Throwable? = null
) : ResponseException(status = 400, message = message, cause = cause)