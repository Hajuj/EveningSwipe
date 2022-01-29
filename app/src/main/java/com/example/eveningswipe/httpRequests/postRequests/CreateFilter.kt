package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class CreateFilter (
    val filter: FilterDto,
    val token: TokenDto
)

data class FilterDto(
    val genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val group_id: Int,
    val max_runtime: Int,
    val max_year: Int,
    val min_year: Int,
    val rating: Int,
    val size: Int,
    val votes: Int
)