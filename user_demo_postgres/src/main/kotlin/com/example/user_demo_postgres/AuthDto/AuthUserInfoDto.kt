package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.TokenDto

data class AuthUserInfoDto(
    val token: TokenDto,
    val userId: Int
)
