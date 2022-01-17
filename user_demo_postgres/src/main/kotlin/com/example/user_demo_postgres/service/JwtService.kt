package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.JwtDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtService {

    private val key = Keys.hmacShaKeyFor("tNO+KhVrTj3B4q0+SEwz/NSvZq7y577jOjvY4uPgAR4=".toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()

    fun createTokenbyId(id: String): JwtDto {

        val jwt = Jwts.builder()
            .setIssuer(id)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
            .signWith(key)
            .compact()

        return JwtDto(jwt)

    }
    fun getDetailsFromToken(token: String): Any{
        val body  = parser.parseClaimsJws(token).body
        return body
    }

    fun getIdFromToken(token: String): Int = parser.parseClaimsJws(token).body.issuer.toInt()



    fun validateToken(token: String): Boolean{
        val claims = parser.parseClaimsJws(token).body
        if (claims.expiration.toInstant() < Instant.now())
            println("is true")
            return true


        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }


}