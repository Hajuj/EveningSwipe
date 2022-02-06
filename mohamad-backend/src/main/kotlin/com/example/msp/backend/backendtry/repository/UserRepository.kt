package com.example.msp.backend.backendtry.repository

import com.example.msp.backend.backendtry.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Int> {

    fun findByEmail(email: String): UserEntity?
}