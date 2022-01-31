package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.TokenDto

data class AuthCheckUserDto(
    val token: String,
    val email: String)
