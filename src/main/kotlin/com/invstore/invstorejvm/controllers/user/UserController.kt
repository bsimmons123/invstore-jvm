package com.invstore.invstorejvm.controllers.user

import com.invstore.invstorejvm.ApiResponse
import com.invstore.invstorejvm.models.users.UserDTO
import com.invstore.invstorejvm.services.ServiceUtils
import com.invstore.invstorejvm.services.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
@Tag(description = "API endpoint for getting user details", name = "User")
class UserController(private val userService: UserService) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/")
    fun checkUserSession(principal: Principal?): ResponseEntity<ApiResponse<UserDTO?>> {
        log.info("GET /api/v1/user")

        val result = userService.findByUsername(principal?.name ?: "")

        return ServiceUtils.handleResult(result)
    }
}
