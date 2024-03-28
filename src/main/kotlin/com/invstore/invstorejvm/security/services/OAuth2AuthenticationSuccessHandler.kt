package com.invstore.invstorejvm.security.services

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2AuthenticationSuccessHandler() : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        // Prepare your successful authentication payload
        request.session.setAttribute("auth", authentication)

        // Set the appropriate HTTP response attributes
        response.sendRedirect("/#/signin?checklogin=true")
    }
}