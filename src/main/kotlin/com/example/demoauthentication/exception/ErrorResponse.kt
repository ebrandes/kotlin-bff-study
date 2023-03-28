package com.example.demoauthentication.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*


class ErrorResponse(status: HttpStatus, val message: String, var stackTrace: String? = null) {

    val code: Int
    val status = status.name

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    val timestamp: Date = Date()

    init {
        code = status.value()
    }
}