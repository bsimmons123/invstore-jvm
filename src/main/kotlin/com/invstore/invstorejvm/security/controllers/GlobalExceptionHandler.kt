package com.invstore.invstorejvm.security.controllers

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<ErrorDetails> {
        // Customize the ErrorDetails according to your requirements
        val errorDetails = ErrorDetails(
            status = HttpStatus.BAD_REQUEST.value(),  // this can be adjusted according to needs
            message = "Database integrity error",
            details = "Database integrity error" // Don't want to give away schema
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException?): ResponseEntity<*> {
        val customMessage =
            ex?.localizedMessage ?: "unknown error"
        return ResponseEntity(customMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException::class)  // This can be any exception you want to handle
    fun handleNotFound(ex: ChangeSetPersister.NotFoundException): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.NOT_FOUND.value(),
            message = "Resource Not Found",
            details = ex.message
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException::class)  // This can be any exception you want to handle
    fun handleBadCredentials(ex: ChangeSetPersister.NotFoundException): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.FORBIDDEN.value(),
            message = "Invalid Login",
            details = ex.message
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MissingAccessException::class)
    fun handleMissingAccess(ex: MissingAccessException): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.NOT_ACCEPTABLE.value(),
            message = "Invalid Request",
            details = ex.message
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    // Handle general exceptions
    @ExceptionHandler(Exception::class)
    fun handleAllUncaughtException(ex: Exception): ResponseEntity<Any> {
        val errorDetails = ErrorDetails(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Unexpected error occurred",
            details = ex.message
        )
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

// ErrorDetails class for the response body
data class ErrorDetails(
    val status: Int,
    val message: String,
    val details: String?
)

class MissingAccessException(message: String?) : java.lang.Exception(message)