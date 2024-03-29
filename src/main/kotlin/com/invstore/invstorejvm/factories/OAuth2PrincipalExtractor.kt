package com.invstore.invstorejvm.factories

import com.invstore.invstorejvm.models.users.User
import com.invstore.invstorejvm.services.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2PrincipalExtractor(
    @Autowired private val userService: UserService
) : DefaultOAuth2UserService() {


    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user: OAuth2User = super.loadUser(userRequest)
        val attributes: Map<String, Any> = user.attributes

        val id = attributes["id"] as String? // Adjust this to the actual attribute name
        if (id != null) {
            val existingUser: User? = userService.findByProviderId(id)

            if (existingUser == null) {
                val newUser = User(
                    providerId = id,
                    email = (attributes["email"] as String?).toString(),
                    isActive = true,
                    name = (attributes["name"] as String?).toString(),
                    username = (attributes["username"] as String?).toString()
                )
                // add any additional necessary mapping.
                userService.create(newUser)
            }}

        return user
    }
}