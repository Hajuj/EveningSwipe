package com.example.user_demo_postgres.returnDto

data class GroupInfoDto(
    val member: List<Int>,
    val filter: List<Int>,
    val name: String
)
