package com.invstore.invstorejvm.security.jwt;

import com.invstore.invstorejvm.security.services.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
@Profile("dev", "prod")
class AuthTokenFilter : OncePerRequestFilter() {

  @Autowired
  private val jwtUtils: JwtUtils? = null

  @Autowired
  private val userDetailsService: UserDetailsServiceImpl? = null

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    try {
      val jwt = parseJwt(request)
      if (jwt != null && jwtUtils!!.validateJwtToken(jwt)) {
        val username = jwtUtils.getUserNameFromJwtToken(jwt)

        val userDetails = userDetailsService!!.loadUserByUsername(username)
        val authentication =
          UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
          )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
      } else if (jwt != null) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
      }
    } catch (e: Exception) {
      Companion.logger.error("Cannot set user authentication: {}", e.message)
    }

    filterChain.doFilter(request, response)
  }

  private fun parseJwt(request: HttpServletRequest): String? {
    val headerAuth = request.getHeader("Authorization")

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7)
    }

    return null
  }

  companion object {
    private val logger: Logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)
  }
}
