package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.TokenDto

data class AuthMovieDetailsDto(
    val token: TokenDto,
    val movieId: String
)
