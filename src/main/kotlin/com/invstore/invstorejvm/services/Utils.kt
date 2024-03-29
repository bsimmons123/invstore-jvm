package com.invstore.invstorejvm.services

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
    inline fun <T> handleServiceCall(call: () -> T, errorList: MutableList<String> = mutableListOf()): T? =
        runCatching {
            call()
        }
            .onFailure {
                errorList.add("Error: ${it.localizedMessage}")
            }
            .getOrNull()
}