package de.sweller.jwt.config

import de.sweller.jwt.auth.ApplicationUserDetailsService
import de.sweller.jwt.auth.AuthenticationFilter
import de.sweller.jwt.auth.AuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource


const val SIGN_UP_URL = "/signup"
const val KEY = "A?D*G-KaPdSgVkYp3s6v9y\$B&E)H+MbQeThWmZq4t7w!z%C*F-JaNcRfUjXn2r5u"
const val HEADER_NAME = "Authorization"
const val HEADER_PREFIX = "Bearer "
const val EXPIRATION_TIME = 1000L * 60 * 30

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val applicationUserDetailsService: ApplicationUserDetailsService,
    val passwordEncoder: PasswordEncoder
): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated().and()
            .addFilter(AuthenticationFilter(authenticationManager()))
            .addFilter(AuthorizationFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(applicationUserDetailsService)
            .passwordEncoder(passwordEncoder)
    }
}
