package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.GroupFilterDTO
import com.example.user_demo_postgres.dto.TokenDto

data class AuthFilterDto(
    val token: TokenDto,
    val filter: GroupFilterDTO
)
