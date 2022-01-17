package com.example.user_demo_postgres.dto

data class GroupDTO(
    var id: Int,
    var name: String,
    var description: String,
    var owner: Int
)
