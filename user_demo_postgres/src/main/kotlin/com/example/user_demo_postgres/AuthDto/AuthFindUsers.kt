package com.example.user_demo_postgres.AuthDto

data class AuthFindUsers(
    val token: String,
    val search: String
)
