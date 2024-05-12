package com.invstore.invstorejvm.security.controllers

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.security.jwt.JwtOperations
import com.invstore.invstorejvm.security.requests.AuthRequests
import com.invstore.invstorejvm.security.requests.JwtResponse
import com.invstore.invstorejvm.security.requests.SignInRequest
import com.invstore.invstorejvm.security.services.UserDetailsImpl
import com.invstore.invstorejvm.services.user.IUserService
import com.invstore.invstorejvm.services.user.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.LocalDateTime

@RestController
@CrossOrigin(origins = ["http://localhost:8082"])
@RequestMapping("/api/v1/auth")
@Profile("dev", "prod")
class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private var jwtUtils: JwtOperations
) {

    @PostMapping("/signup")
    fun registerUser(@RequestBody newUser: AuthRequests): ResponseEntity<Any> {
        if (userService.existsByUsername(newUser.username)) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!")
        }
        if (userService.existsByEmail(newUser.email)) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!")
        }

        // Create new user's account
        val user = User(
            email = newUser.email,
            username = newUser.username,
            isActive = true,
            name = newUser.username,
            password = passwordEncoder.encode(newUser.password)
        )

        val savedUser = userService.create(user)

        return ResponseEntity.ok(savedUser!!.id)
    }

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: SignInRequest,
                         request: HttpServletRequest,
                         response: HttpServletResponse
    ): ResponseEntity<*> {
        val authentication = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)

        // The authenticated user
        val userDetails = authentication.principal as UserDetailsImpl
        val user  = userService.findByEmailOrUsername(loginRequest.email, loginRequest.email)
        if (user != null) {
            user.lastLoggedIn = LocalDateTime.now()
            userService.update(user)
        }

        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                userDetails.username,
                userDetails.authorities.map { it.authority },
            )
        )
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<String> {
        SecurityContextHolder.getContext().authentication = null
        return ResponseEntity.ok("Logged out successfully")
    }

    @GetMapping("/session/oauth/callback")
    fun getLoggedInUser(request: HttpServletRequest, principal: Principal?): ResponseEntity<Any> {
        try {
            val oldJwt = parseJwt(request)
            val isValid = jwtUtils.validateJwtToken(oldJwt)
            if (oldJwt == null || !isValid) {
                val auth = request.session.getAttribute("auth") as Authentication
                SecurityContextHolder.getContext().authentication = auth
                val jwt = jwtUtils.generateJwtTokenOauth(auth)
                return ResponseEntity.ok(jwt)
            }
            return ResponseEntity.ok(oldJwt)
        } catch (_: Exception) {
            return ResponseEntity.badRequest().body("Error: JWT is invalid, or user is not authenticated")
        }
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        try {
            val headerAuth = request.getHeader("Authorization")

            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7)
            }

        } catch (e: Exception) {
            throw e
        }

        return null
    }
}