package com.example.msp.backend.backendtry.controller

import com.example.msp.backend.backendtry.dto.LoginDTO
import com.example.msp.backend.backendtry.dto.RegisterDTO
import com.example.msp.backend.backendtry.entity.UserEntity
import com.example.msp.backend.backendtry.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping
class AuthController(private val userService: UserService) {

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<UserEntity> {
        val user = UserEntity(
            name = body.fullName,
            email = body.email,
            password = body.password,
            id = 0
        )
        return ResponseEntity.ok(this.userService.save(user))
    }

    //TODO: complete!
    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, respnse: HttpServletResponse): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(("User does not exist"))

        return ResponseEntity.ok("Successful")
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok("Successful")
    }

    //TODO: complete!
    @GetMapping("user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Not authorized")
        }
        return ResponseEntity.ok("Successful")
    }
}