package com.example.user_demo_postgres.AuthDto

import antlr.Token
import com.example.user_demo_postgres.dto.TokenDto

data class AuthGetFilterDto(
    val token: TokenDto,
    val groupId: Int
)
