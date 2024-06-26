package com.invstore.invstorejvm.factories

import com.invstore.invstorejvm.models.users.MyUserPrincipal
import com.invstore.invstorejvm.repositories.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class MyUserDetailsService(@Autowired private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return MyUserPrincipal(user)
    }
}