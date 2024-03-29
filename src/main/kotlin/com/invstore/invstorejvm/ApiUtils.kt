package com.invstore.invstorejvm

class ApiUtils {


}

data class ApiResponse<T>(val success: Boolean, val value: T?, val errors: List<String>?)