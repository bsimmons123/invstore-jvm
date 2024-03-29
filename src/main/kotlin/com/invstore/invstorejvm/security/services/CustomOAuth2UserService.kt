package com.invstore.invstorejvm.security.services

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.services.user.IUserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import java.time.LocalDateTime
import java.util.Collections.singletonList

class CustomOAuth2UserService(
    private val userService: IUserService
    ) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val email = oAuth2User.attributes["email"] as String? ?: ""
        val name = oAuth2User.attributes["username"] as String? ?: ""
        val username = oAuth2User.attributes["login"] as String? ?: ""
        val provider: String = userRequest.clientRegistration.registrationId
        val providerId: Int = oAuth2User.attributes["id"] as Int? ?: 0

        val user = userService.findByEmailOrUsername(email, username) ?:
            User(
                email = email,
                name = name,
                username = username,
                provider = provider,
                providerId = providerId.toString(),
                isActive = true
            )

        // update the last logged in attrib
        user.lastLoggedIn = LocalDateTime.now()

        userService.create(user) // Save/update user in DB

        return DefaultOAuth2User(
            singletonList(SimpleGrantedAuthority("ROLE_USER")),
            oAuth2User.attributes,
            "login" // Name of the attribute that acts as username
        )
    }
}