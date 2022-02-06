package com.example.msp.backend.backendtry.repository

import com.example.msp.backend.backendtry.entity.MovieEntity
import com.example.msp.backend.backendtry.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<MovieEntity, Int> {

    fun findByEmail(email: String): UserEntity?
}