package com.invstore.invstorejvm.services

import com.invstore.invstorejvm.ApiResponse
import org.springframework.http.ResponseEntity

fun String.isValidNonPositiveInt(): Boolean {
    return try {
        val number = this.toInt()
        number <= 0
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.isValidPositiveInt(): Boolean {
    return try {
        val number = this.toInt()
        number > 0
    } catch (e: NumberFormatException) {
        false
    }
}

object ServiceUtils {

    fun <T> createResponse(result: T?, errorList: MutableMap<String, String>): ResponseEntity<ApiResponse<T>> {
        return if (result != null) {
            ResponseEntity.ok(ApiResponse(true, result, null))
        } else {
            ResponseEntity.ok(ApiResponse(true, null, errorList))
        }
    }

    fun <T> handleResult(result: OperationResult<T>) : ResponseEntity<ApiResponse<T>> {
        return when (result) {
            is OperationResult.Success -> {
                ResponseEntity.ok(ApiResponse(true, result.data, null))
            }

            is OperationResult.Error -> {
                ResponseEntity.ok(ApiResponse(false, null, result.errors))
            }
        }
    }
}

sealed class OperationResult<T> {
    data class Success<T>(val data: T) : OperationResult<T>()
    data class Error<T>(val errors: MutableMap<String, String>) : OperationResult<T>()
}

