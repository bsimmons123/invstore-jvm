package com.invstore.invstorejvm.security.controllers

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.security.jwt.JwtUtils
import com.invstore.invstorejvm.security.requests.AuthRequests
import com.invstore.invstorejvm.security.requests.JwtResponse
import com.invstore.invstorejvm.security.requests.SignInRequest
import com.invstore.invstorejvm.security.services.UserDetailsImpl
import com.invstore.invstorejvm.services.user.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:8082"])
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
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
        val user = User()

        user.username = newUser.username
        user.email = newUser.email

        user.password = passwordEncoder.encode(newUser.password)

        val savedUser = userService.save(user)

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
    fun getLoggedInUser(request: HttpServletRequest): ResponseEntity<Any> {
        val auth = request.session.getAttribute("auth") as Authentication
        SecurityContextHolder.getContext().authentication = auth
        val jwt = jwtUtils.generateJwtTokenOauth(auth)
        return ResponseEntity.ok(jwt)
    }
}