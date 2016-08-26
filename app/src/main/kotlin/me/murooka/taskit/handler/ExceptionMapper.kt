package me.murooka.taskit.handler

import me.murooka.taskit.exception.ResponseException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper as BaseExceptionMapper

data class ErrorResponse(val message: String)

class ExceptionMapper : BaseExceptionMapper<Throwable> {
    override fun toResponse(exception: Throwable?): Response {
        if (exception == null) {
            println("exception is null")
            return Response
                    .status(500)
                    .header("Content-Type", "application/json")
                    .entity(ErrorResponse("Internal server error"))
                    .build()
        } else if (exception is ResponseException) {
            return Response
                    .status(exception.status)
                    .header("Content-Type", "application/json")
                    .entity(ErrorResponse(exception.message!!))
                    .build()
        } else {
            println("unknown exception, e = " + exception.toString())
            return Response
                    .status(500)
                    .header("Content-Type", "application/json")
                    .entity(ErrorResponse("Internal server error"))
                    .build()
        }

    }
}
