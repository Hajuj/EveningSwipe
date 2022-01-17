package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.GroupDTO
import com.example.user_demo_postgres.dto.TokenDto

data class AuthGroupDto(
    val token: TokenDto,
    val group: GroupDTO

)
