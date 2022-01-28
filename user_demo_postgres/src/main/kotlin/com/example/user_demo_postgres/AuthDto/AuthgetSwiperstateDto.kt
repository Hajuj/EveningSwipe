package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.TokenDto

data class AuthgetSwiperstateDto(
    val token: TokenDto,
    val filterId: Int
)
