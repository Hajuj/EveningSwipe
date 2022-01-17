package com.example.user_demo_postgres.entity

import javax.persistence.*

@Entity

data class GroupUsers(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,
    val groupId: Int,
    val userId: Int
)
