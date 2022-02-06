package com.example.msp.backend.backendtry.service

import com.example.msp.backend.backendtry.entity.UserEntity
import com.example.msp.backend.backendtry.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findByEmail(email: String): UserEntity? {
        return this.userRepository.findByEmail(email)
    }

    fun save(userEntity: UserEntity): UserEntity {
        return this.userRepository.save(userEntity)
    }
}