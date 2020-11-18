package io.github.masterj3y.sorna.auth.config

data class AuthPrincipal(
        var userId: String = "",
        var accessToken: String = ""
)