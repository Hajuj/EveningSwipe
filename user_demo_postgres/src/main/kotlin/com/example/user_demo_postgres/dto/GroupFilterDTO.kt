package com.example.user_demo_postgres.dto

data class GroupFilterDTO(

    var id: Int ,
    var name: String,
    var genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val min_year: Int,
    val max_year: Int,
    val rating: Double,
    val votes: Int,
    val max_runtime: Int,
    val size: Int,
    val group_id: Int,
    var selection: List<String> = emptyList()
)
