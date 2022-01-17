package com.example.user_demo_postgres.controller
import com.example.user_demo_postgres.dto.LoginDTO
import com.example.user_demo_postgres.dto.RegisterDTO
import com.example.user_demo_postgres.dto.RequestDTO
import com.example.user_demo_postgres.entity.UserApp
import com.example.user_demo_postgres.service.EncoderService
import com.example.user_demo_postgres.service.JwtService
import com.example.user_demo_postgres.service.RegexService
import com.example.user_demo_postgres.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
class AuthController (
    private val userService: UserService,
    private val encoderService: EncoderService,
    private val jwtService: JwtService,
    private val regexService: RegexService
        ){
    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<Any> {
        if (regexService.isEmailFormatValid(body.email) and regexService.isPasswordStrongEnough(body.password)) {
            val user = userService.mapRegisterTouser(body)
            return ResponseEntity.ok(this.userService.save(user))
        }
        throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }


    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): ResponseEntity<Any>{
        val user = userService.findByEmail(login.email)

        user?.let{
            if(encoderService.encoder.matches(login.password, user.password))
                return ResponseEntity.ok(jwtService.createTokenbyId(user.id.toString()))



        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/user")
    fun getUser(@RequestBody requestDTO: RequestDTO?): ResponseEntity<Any>{

        requestDTO?.let{

            return ResponseEntity.ok( jwtService.getDetailsFromToken(requestDTO.token))



        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)


    }

    /*@PostMapping("login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<Any>{
        print("meail ${body.email} password ${body.password}")
        val user = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body((ResponseMessage("user not found")))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body((ResponseMessage("invalid password")))

        }

        return ResponseEntity.ok(user)

    }*/

    /*@PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): Jwt {
        val user = userService.findByEmail(login.email)


        user?.let {
            print("user ${user.email} ${user.password}")
            if(encoder.matches(login.password, it.password))
                return Jwt(tokenService.generate(it.email).value)
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }
    data class Profile(val email: String)*/
}