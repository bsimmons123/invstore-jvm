package com.invstore.invstorejvm.security.services

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.repositories.users.UserRepository
import com.invstore.invstorejvm.security.jwt.JwtUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.Collections.singletonList

class CustomOAuth2UserService(
    private val userRepository: UserRepository
    ) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val jwtUtils = JwtUtils()

        val email = oAuth2User.attributes["email"] as String? ?: ""
        val name = oAuth2User.attributes["username"] as String? ?: ""
        val username = oAuth2User.attributes["login"] as String? ?: ""
        val provider: String = userRequest.clientRegistration.registrationId
        val providerId: Int = oAuth2User.attributes["id"] as Int? ?: 0

        val user = userRepository.findByEmailOrUsername(email, username) ?:
            User(
                email = email,
                name = name,
                username = username,
                provider = provider,
                providerId = providerId.toString()
            )


        userRepository.save(user) // Save/update user in DB

        return DefaultOAuth2User(
            singletonList(SimpleGrantedAuthority("ROLE_USER")),
            oAuth2User.attributes,
            "login" // Name of the attribute that acts as username
        )
    }
}