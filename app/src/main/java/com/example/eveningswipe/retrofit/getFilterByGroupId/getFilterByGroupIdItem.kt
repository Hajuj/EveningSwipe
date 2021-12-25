package com.example.eveningswipe.retrofit.getFilterByGroupId

data class getFilterByGroupIdItem(
    val genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val group_id: Int,
    val id: Int,
    val max_runtime: Int,
    val max_year: Int,
    val min_year: Int,
    val rating: Double,
    val selection: List<String>,
    val size: Int,
    val votes: Int
)