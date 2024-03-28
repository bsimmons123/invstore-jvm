package com.invstore.invstorejvm.controllers

import com.invstore.invstorejvm.security.jwt.JwtUtils
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
@Tag(description = "API endpoint for checking user session", name = "User")
class UserController(
    private val jwtUtils: JwtUtils
) {

    @GetMapping("/")
    fun checkUserSession(principal: Principal?): ResponseEntity<Any> {
        val auth = SecurityContextHolder.getContext().authentication ?: return ResponseEntity.status(401).build()

        if (auth is AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).build()
        }

        val jwt = jwtUtils.generateJwtToken(auth)

        return ResponseEntity.ok().body(jwt)
    }
}