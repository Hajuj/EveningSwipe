package com.example.user_demo_postgres.repository

import com.example.user_demo_postgres.entity.UserApp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<UserApp, Int> {

    fun findByEmail(email: String): UserApp?

}