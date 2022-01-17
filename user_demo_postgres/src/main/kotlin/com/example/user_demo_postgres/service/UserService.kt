package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.RegisterDTO
import com.example.user_demo_postgres.entity.UserApp
import com.example.user_demo_postgres.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService (
    private val userRepository: UserRepository,
    private val encoderService: EncoderService
        ){

    fun save(userApp: UserApp): UserApp {
        return this.userRepository.save(userApp)
    }

    fun findByEmail(email: String ): UserApp?{
        return this.userRepository.findByEmail(email)

    }
    fun mapRegisterTouser(register: RegisterDTO): UserApp{
        return UserApp( 0,register.name, register.email, encoderService.encoder.encode(register.password))
    }

    fun getUserById(userId: Int): UserApp?{
         return userRepository.findByIdOrNull(userId)
    }
}