package com.invstore.invstorejvm.security.services

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.repositories.users.UserRepository
import com.invstore.invstorejvm.services.user.UserService
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Primary
class UserDetailsServiceImpl(repository: UserRepository) : UserDetailsService {
    private val userService: UserService = UserService(repository)

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByEmailOrUsername(username, username)
            ?: throw UsernameNotFoundException("User '$username' not found")

        // assuming that your UserDetailsImpl accepts username and password in constructor
        return UserDetailsImpl.build(user)
    }
}