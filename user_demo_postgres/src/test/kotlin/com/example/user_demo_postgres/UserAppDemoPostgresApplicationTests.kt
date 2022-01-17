package com.example.user_demo_postgres

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


class UserAppDemoPostgresApplicationTests {

    @Test
    fun generateKeys() {
        for(index in 1..10){
            val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
            println(Encoders.BASE64.encode(key.encoded))
        }
    }

}
