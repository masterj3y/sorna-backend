package io.github.masterj3y.sorna.auth.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders
import java.util.*
import javax.crypto.SecretKey

object JwtConfig {

    var accessTokenPrefix: String = "Bearer "

    const val authorizationHeader: String = HttpHeaders.AUTHORIZATION

    private var jwtSign: SecretKey? = null

    fun getJwtSign(): SecretKey {
        if (jwtSign == null)
            jwtSign = Keys.hmacShaKeyFor("r_78HpIFjh5wW_HFxwoccgwJoBMXcefz8NYzJo6vcxvEEgDSgfsdUnxUVWTQ7oaYxaTdCtPhqWH30yPzqV5U2aRgyQBHqzvpEivXY6z1-S_Dhw&_rrEEwETEZ8HXZnJXjdcJ2z1HaaG0t4Q".toByteArray())
        return jwtSign!!
    }

    fun getAccessTokenExpiration(): Date {
        return Date(System.currentTimeMillis() + (31 * 24 * 60 * 60 * 1000))
    }

}