package com.invstore.invstorejvm.security

import com.invstore.invstorejvm.repositories.users.UserRepository
import com.invstore.invstorejvm.security.jwt.AuthEntryPointJwt
import com.invstore.invstorejvm.security.jwt.AuthTokenFilter
import com.invstore.invstorejvm.security.services.CustomOAuth2UserService
import com.invstore.invstorejvm.security.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository

@Configuration
@EnableMethodSecurity
class WebSecurityConfig {
    @Autowired
    var userDetailsService: UserDetailsServiceImpl? = null

    @Autowired
    private val unauthorizedHandler: AuthEntryPointJwt? = null

    @Autowired
    private val userRepository: UserRepository? = null

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
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
    fun oauthUserService(userRepository: UserRepository): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
        return CustomOAuth2UserService(userRepository)
    }

//    @Bean
//    fun csrfTokenRepository(): CsrfTokenRepository {
//        return HttpSessionCsrfTokenRepository()
//    }

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
                auth.requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .requestMatchers("/", "/static/**", "/favicon.png", "/index.html").permitAll()
                    .requestMatchers("/oauth2/authorization/github", "/login/oauth2/code/github").permitAll()
                    .anyRequest().authenticated()
            }
            .csrf{c -> c
                .disable() // disable for now
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(CsrfTokenRequestAttributeHandler())
//                .csrfTokenRepository(csrfTokenRepository())
            }
            .oauth2Login { oauth: OAuth2LoginConfigurer<HttpSecurity?> ->
                oauth.loginPage("/#/signin").permitAll()
                oauth.defaultSuccessUrl("/#/dashboard")
                oauth.failureUrl("/#/signin").permitAll()
                oauth.userInfoEndpoint {
                    it.userService(oauthUserService(userRepository!!))
                }
            } // OpenID Connect with GitHub


        http.authenticationProvider(authenticationProvider())

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}