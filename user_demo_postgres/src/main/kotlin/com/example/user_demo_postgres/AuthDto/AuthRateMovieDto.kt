package com.example.user_demo_postgres.AuthDto

import com.example.user_demo_postgres.dto.RateMovieDto
import com.example.user_demo_postgres.dto.TokenDto

data class AuthRateMovieDto(
    val token: TokenDto,
    val rating: RateMovieDto
)
