package io.github.masterj3y.sorna

import io.github.masterj3y.sorna.auth.jwt.JwtHelper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SornaApplicationTests {

    @Test
    fun contextLoads() {
        val jwtHelper = JwtHelper()
        println(jwtHelper.createAccessToken("fe807765-13ce-4905-a835-49d039862c0a"))
    }

}
