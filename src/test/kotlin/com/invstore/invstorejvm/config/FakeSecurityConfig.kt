package com.invstore.invstorejvm.config

import com.invstore.invstorejvm.security.jwt.AuthEntryPointJwt
import com.invstore.invstorejvm.security.jwt.AuthTokenFilter
import com.invstore.invstorejvm.security.services.CustomOAuth2UserService
import com.invstore.invstorejvm.security.services.OAuth2AuthenticationSuccessHandler
import com.invstore.invstorejvm.security.services.UserDetailsServiceImpl
import com.invstore.invstorejvm.services.user.IUserService
import jakarta.servlet.Filter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.util.*

@Configuration
@EnableMethodSecurity
@Profile("test")
class FakeSecurityConfig {
    @Autowired
    var userDetailsService: UserDetailsServiceImpl? = null

    @Autowired
    private val unauthorizedHandler: AuthEntryPointJwt? = null

    @Autowired
    private val userService: IUserService? = null

    @Autowired
    private val authenticationSuccessHandler: OAuth2AuthenticationSuccessHandler? = null

    @Bean
    fun authenticationJwtTokenFilter(): FakeAuthTokenFilter {
        return FakeAuthTokenFilter()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()

        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())

        return authProvider
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(authenticationProvider()))
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun oauthUserService(userService: IUserService, userDetailsServiceImpl: UserDetailsServiceImpl): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
        return CustomOAuth2UserService(userService)
    }

    @Bean
    fun simpleCorsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = false
        // *** URL below needs to match the Vue client URL and port ***
        config.allowedOrigins = Collections.singletonList("*")
        config.allowedMethods = Collections.singletonList("*")
        config.allowedHeaders = Collections.singletonList("*")
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean<Filter>(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.exceptionHandling { exception: ExceptionHandlingConfigurer<HttpSecurity?> ->
            exception.authenticationEntryPoint(
                unauthorizedHandler
            )
        }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                    auth.anyRequest().permitAll()
            }
            .csrf{c -> c
                .disable() // disable for now
            }

        return http.build()
    }
}