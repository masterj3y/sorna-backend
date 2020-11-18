package io.github.masterj3y.sorna.auth.jwt

import io.github.masterj3y.sorna.auth.config.AuthPrincipal
import io.github.masterj3y.sorna.auth.config.OAuthAuthenticationToken
import io.jsonwebtoken.JwtException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtVerifier(private val jwtHelper: JwtHelper) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val authorizationHeader = request.getHeader(JwtConfig.authorizationHeader)

        if (authorizationHeader.isNullOrEmpty() || authorizationHeader.startsWith(JwtConfig.accessTokenPrefix).not()) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.replace(JwtConfig.accessTokenPrefix, "")

        try {

            val claimsJws = jwtHelper.extractClaimsJws(token)

            val claims = claimsJws.body
            val userId = claims.subject

            if (jwtHelper.validateAccessToken(token)) {

                val authentication = OAuthAuthenticationToken(AuthPrincipal(userId, token), null, null)
                SecurityContextHolder.getContext().authentication = authentication

            } else {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
            }

        } catch (e: JwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }

        filterChain.doFilter(request, response)

    }

}