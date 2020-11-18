package io.github.masterj3y.sorna.features.user

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import io.github.masterj3y.sorna.Constants.GOOGLE_CLIENT_ID
import io.github.masterj3y.sorna.auth.config.AuthenticationResponse
import io.github.masterj3y.sorna.auth.jwt.JwtHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ApplicationUserService @Autowired constructor(private val repository: ApplicationUserRepository,
                                                    private val jwtHelper: JwtHelper) {

    fun loginWithGoogle(googleIdToken: String): AuthenticationResponse {

        val idToken = verifyGoogleIdToken(googleIdToken)
        val payload = idToken.payload
        val firstName = payload["given_name"] as String
        val lastName = payload["family_name"] as String
        val email = payload.email
        val picUrl = payload["picture"] as String
        val googleId = payload.subject

        val applicationUser = findUserByGoogleId(googleId)

        return if (applicationUser.isPresent)
            logIn(applicationUser.get())
        else
            signIn(firstName, lastName, email, picUrl, googleId)

    }

    private fun verifyGoogleIdToken(googleIdToken: String): GoogleIdToken {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), JacksonFactory())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build()

        return verifier.verify(googleIdToken) ?: throw IllegalStateException("google id is not valid")
    }

    private fun signIn(firstName: String, lastName: String, email: String, picUrl: String, googleId: String): AuthenticationResponse {
        val userId = UUID.randomUUID()
        val accessToken = jwtHelper.createAccessToken(userId.toString())

        saveUser(
                ApplicationUser(
                        id = userId,
                        firstName = firstName,
                        lastName = lastName,
                        profilePic = picUrl,
                        googleId = googleId,
                        email = email
                )
        )

        return AuthenticationResponse(accessToken)
    }

    private fun logIn(applicationUser: ApplicationUser): AuthenticationResponse {
        val accessToken = jwtHelper.refreshAccessToken(applicationUser)
        saveUser(applicationUser)
        return AuthenticationResponse(accessToken)
    }

    fun findUserByGoogleId(googleId: String): Optional<ApplicationUser> {
        return repository.findByGoogleId(googleId)
    }

    fun saveUser(applicationUser: ApplicationUser): ApplicationUser {
        return repository.save(applicationUser)
    }

}