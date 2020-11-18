package io.github.masterj3y.sorna.features.user

import io.github.masterj3y.sorna.auth.jwt.JwtConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class ApplicationUserController @Autowired constructor(private val service: ApplicationUserService) {

    @PostMapping("/google")
    fun loginWithGoogle(response: HttpServletResponse, request: HttpServletRequest) {
        val authenticationResponse = service.loginWithGoogle(request.getHeader("googleIdToken"))
        response.addHeader(JwtConfig.authorizationHeader, authenticationResponse.accessToken)
    }
}