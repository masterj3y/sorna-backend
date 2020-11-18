package io.github.masterj3y.sorna.auth.config

import io.github.masterj3y.sorna.auth.jwt.JwtHelper
import io.github.masterj3y.sorna.auth.jwt.JwtVerifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApplicationSecurity @Autowired constructor(private val jwtHelper: JwtHelper)
    : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http
                ?.csrf()?.disable()
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ?.and()
                ?.addFilterBefore(JwtVerifier(jwtHelper), UsernamePasswordAuthenticationFilter::class.java)
        //?.authorizeRequests()
        //?.antMatchers("/auth/**")?.permitAll()
        //?.anyRequest()
        //?.authenticated()
    }

}