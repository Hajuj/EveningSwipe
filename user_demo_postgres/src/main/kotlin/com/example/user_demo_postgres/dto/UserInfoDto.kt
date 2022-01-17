package com.example.user_demo_postgres.dto

data class UserInfoDto(
    val userName: String,
    val email: String,
    val groupId: List<Int>,
    val adminGroupId: List<Int>
)
