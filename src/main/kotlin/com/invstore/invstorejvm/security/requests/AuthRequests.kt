package com.invstore.invstorejvm.security.requests

import org.springframework.security.web.csrf.CsrfToken

data class AuthRequests(
    val username: String,
    val email: String,
    val password: String
)

data class SignInRequest(val email: String, val password: String)
data class JwtResponse(val jwtToken: String, val username: String, val roles: List<String>, val csrfToken: CsrfToken? = null)