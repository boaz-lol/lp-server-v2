package com.boaz.lp.admin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class AdminSecurityConfig(
    @Value("\${admin.username}") private val username: String,
    @Value("\${admin.password}") private val password: String
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsManager(passwordEncoder: PasswordEncoder): InMemoryUserDetailsManager {
        val user = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers("/login", "/css/**", "/js/**", "/actuator/**").permitAll()
                it.anyRequest().authenticated()
            }
            .formLogin {
                it.loginPage("/login")
                it.defaultSuccessUrl("/", true)
                it.permitAll()
            }
            .logout {
                it.logoutSuccessUrl("/login?logout")
                it.permitAll()
            }

        return http.build()
    }
}
