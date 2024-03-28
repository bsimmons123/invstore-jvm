package com.invstore.invstorejvm.security.jwt

import org.springframework.security.core.Authentication;

interface JwtOperations {
    fun generateJwtToken(authentication: Authentication): String
    fun generateJwtTokenOauth(authentication: Authentication): String
    fun getUserNameFromJwtToken(token: String?): String
    fun validateJwtToken(authToken: String?): Boolean
}