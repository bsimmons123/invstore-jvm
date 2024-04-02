package com.invstore.invstorejvm.security.services

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException?): ResponseEntity<*> {
        // Replace the following message with a more user-friendly message
        val customMessage =
            ex?.localizedMessage ?: "unknown error"
        return ResponseEntity(customMessage, HttpStatus.BAD_REQUEST)
    } // ... more handlers for other exceptions can go here ...
}