package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class CreateFilter (
    val filter: FilterDto,
    val token: TokenDto
)

data class FilterDto(
    var genre_1: String,
    val genre_2: String,
    val genre_3: String,
    val group_id: Int,
    var max_runtime: Int,
    var max_year: Int,
    var min_year: Int,
    val rating: Int,
    var size: Int,
    val votes: Int
)