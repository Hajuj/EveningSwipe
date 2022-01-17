package com.example.user_demo_postgres.dto

data class MovieRatingDTO(
    val id: Int,
    val movie_id: String,
    val filter_id: Int,
    val rating: Int
)
