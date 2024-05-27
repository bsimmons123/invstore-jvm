package com.invstore.invstorejvm.config;

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
@Profile("test")
class FakeAuthTokenFilter : OncePerRequestFilter() {

  @Autowired
  private val userDetailsService: UserDetailsServiceImpl? = null

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val userDetails = userDetailsService!!.loadUserByUsername("raphie")
    val authentication =
      UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.authorities
      )
    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

    SecurityContextHolder.getContext().authentication = authentication

    filterChain.doFilter(request, response)
  }
}
