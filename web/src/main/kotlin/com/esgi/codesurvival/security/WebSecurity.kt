package com.esgi.codesurvival.security

import io.jkratz.mediator.core.Mediator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
open class WebSecurity(
    private val authProvider: AuthTokenSecurityProvider,
    @Autowired private val mediator : Mediator
) : WebSecurityConfigurerAdapter() {


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().cors().and()
            .authorizeRequests()
            .antMatchers("/users/registration").permitAll()
            .antMatchers("/users").permitAll()
            .antMatchers("/users/authentication").permitAll()
            .antMatchers(
                    "/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**").permitAll()
            .anyRequest().authenticated()
        http.addFilterAfter(JWTAuthorizationFilter(super.authenticationManager(), mediator), JWTAuthenticationFilter::class.java)

    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }

    @Bean
    fun generateAuthenticationFilter() : JWTAuthenticationFilter {
        return JWTAuthenticationFilter(super.authenticationManager(), mediator)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().applyPermitDefaultValues()
        configuration.allowedMethods = HttpMethod.values().map { it.toString() }
        configuration.addExposedHeader(HttpHeaders.CONTENT_LENGTH)
        configuration.addExposedHeader(HttpHeaders.CONTENT_TYPE)
        configuration.addExposedHeader(HttpHeaders.LOCATION)
        configuration.addAllowedHeader("*")
        configuration.addAllowedOrigin("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}