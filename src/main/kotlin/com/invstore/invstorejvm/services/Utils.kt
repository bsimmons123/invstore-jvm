package com.invstore.invstorejvm.services

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.services.catering.CateringListService
import com.invstore.invstorejvm.services.user.UserService
import org.springframework.http.ResponseEntity
import java.security.Principal

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

    fun <T> createResponse(result: T?, errorList: MutableMap<String, String> = mutableMapOf(Pair("", ""))): ResponseEntity<ApiResponse<T>> {
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

object AccessManagement {
    fun extractUser(principal: Principal, userService: UserService): UserDTO? {
        return userService.findByUsername(principal.name).data
    }

    fun <T> extractObjectNull(operationResult: OperationResult<T>) : T? {
        return when (operationResult) {
            is OperationResult.Error -> null
            is OperationResult.Success -> operationResult.data
        }
    }

    fun hasAccessToList(listId: Long, principal: Principal, userService: UserService, cateringListService: CateringListService): Boolean {
        val usr = extractUser(principal, userService)
        val lst = extractObjectNull(cateringListService.findById(listId))
        return lst?.user?.id == usr!!.id
    }
}

sealed class OperationResult<T> {
    data class Success<T>(val data: T) : OperationResult<T>()
    data class Error<T>(val errors: MutableMap<String, String> = mutableMapOf(Pair("", ""))) : OperationResult<T>()
}

