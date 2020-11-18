package io.github.masterj3y.sorna.auth.jwt

import io.github.masterj3y.sorna.features.user.ApplicationUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtHelper {

    fun refreshAccessToken(applicationUser: ApplicationUser): String {
        return createAccessToken(applicationUser.id.toString())
    }

    fun createAccessToken(
            userId: String): String {

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(Date())
                .setExpiration(JwtConfig.getAccessTokenExpiration())
                .signWith(JwtConfig.getJwtSign())
                .compact()
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = extractClaimsBody(token)
        val isTokenExpired = claims.expiration.before(Date())
        return !isTokenExpired
    }

    fun extractClaimsJws(token: String): Jws<Claims> {
        return Jwts.parserBuilder().setSigningKey(JwtConfig.getJwtSign()).build().parseClaimsJws(token)
    }

    fun extractSubject(token: String): String {
        return Jwts.parserBuilder().setSigningKey(JwtConfig.getJwtSign()).build().parseClaimsJws(token).body.subject
    }

    private fun extractClaimsBody(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(JwtConfig.getJwtSign()).build().parseClaimsJws(token).body
    }

}