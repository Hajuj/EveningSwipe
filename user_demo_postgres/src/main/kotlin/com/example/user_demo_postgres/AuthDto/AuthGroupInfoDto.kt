package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.TokenDto

data class AuthGroupInfoDto(
    val token: TokenDto,
    val groupId : Int
)
