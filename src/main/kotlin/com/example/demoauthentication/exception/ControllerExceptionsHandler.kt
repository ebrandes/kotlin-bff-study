package com.example.demoauthentication.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import kotlin.NoSuchElementException


@ControllerAdvice
class ControllerExceptionsHandler {

    @ExceptionHandler(HttpClientErrorException.BadRequest::class, MissingServletRequestParameterException::class, IllegalArgumentException::class)
    fun constraintViolationException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Requisição inválida", e)
    }

    @ExceptionHandler(AuthorizationException::class)
    fun unauthorizedException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.FORBIDDEN, "Não autorizado", e)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun forbiddenException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "Operação não permitida", e)
    }

    @ExceptionHandler(NoSuchElementException::class, NoResultException::class, IndexOutOfBoundsException::class, KotlinNullPointerException::class)
    fun notFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.NOT_FOUND, e.message.orEmpty(), e)
    }

    @ExceptionHandler(Exception::class)
    fun internalServerErrorException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro genérico", e)
    }

    private fun generateErrorResponse(status: HttpStatus, message: String, e: Exception): ResponseEntity<ErrorResponse> {
        // converting the exception stack trace to a string
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        val stackTrace = sw.toString()


        val stackTraceMessage = when (System.getenv("ENV").uppercase()) {
            "HLG" -> stackTrace
            "PRD" -> null
            else -> stackTrace
        }

        // example: logging the stack trace
        // log.debug(stackTrace)
        return ResponseEntity(ErrorResponse(status, message, stackTraceMessage), status)
    }

}