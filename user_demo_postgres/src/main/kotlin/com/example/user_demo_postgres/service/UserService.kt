package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.dto.RegisterDTO
import com.example.user_demo_postgres.dto.RetUserEmNaDto
import com.example.user_demo_postgres.entity.UserApp
import com.example.user_demo_postgres.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class UserService (
    private val userRepository: UserRepository,
    private val encoderService: EncoderService
        ){

    fun save(userApp: UserApp): UserApp = this.userRepository.save(userApp)

    fun findByEmail(email: String ): UserApp? = this.userRepository.findByEmail(email)

    fun mapRegisterTouser(register: RegisterDTO): UserApp =
        UserApp( 0,register.name, register.email, encoderService.encoder.encode(register.password))

    fun getUserById(userId: Int): UserApp? = userRepository.findByIdOrNull(userId)

    fun getAllUsers(): List<RetUserEmNaDto>{
        val users = userRepository.findAll()
        val retUsers = mutableListOf<RetUserEmNaDto>()
        users?.map {
            retUsers.add(RetUserEmNaDto(it.email, it.name))
        }
        return retUsers

    }

    fun findUsers (search: String): List<RetUserEmNaDto>{
        val users = userRepository.findUsersbyString(search)
        val retUsers = mutableListOf<RetUserEmNaDto>()
        users?.map {
            retUsers.add(RetUserEmNaDto(it.email, it.name))
        }
        if( retUsers == emptyList<RetUserEmNaDto>()){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)

        }
        return retUsers
    }

}