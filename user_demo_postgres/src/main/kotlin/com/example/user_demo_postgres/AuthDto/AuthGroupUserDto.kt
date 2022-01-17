package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.GroupUserDto
import com.example.user_demo_postgres.dto.TokenDto

data class AuthGroupUserDto(
    val token: TokenDto,
    val add: GroupUserDto
)
