package com.boaz.lp.api.config

import com.boaz.lp.api.auth.jwt.JwtAuthFilter
import com.boaz.lp.api.auth.jwt.JwtProvider
import com.boaz.lp.api.auth.oauth.CustomOAuth2UserService
import com.boaz.lp.api.auth.oauth.OAuth2FailureHandler
import com.boaz.lp.api.auth.oauth.OAuth2SuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2SuccessHandler: OAuth2SuccessHandler,
    private val oAuth2FailureHandler: OAuth2FailureHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**", "/oauth2/**", "/actuator/**").permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2Login {
                it.userInfoEndpoint { endpoint -> endpoint.userService(customOAuth2UserService) }
                it.successHandler(oAuth2SuccessHandler)
                it.failureHandler(oAuth2FailureHandler)
            }
            .addFilterBefore(JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
