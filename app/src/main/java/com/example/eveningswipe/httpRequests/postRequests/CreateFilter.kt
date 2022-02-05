package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class CreateFilter (
    val token: TokenDto,
    val filter: FilterDto
)

data class FilterDto(
    var name: String,
    var genre_1: String,
    var genre_2: String,
    var genre_3: String,
    var min_year: Int,
    var max_year: Int,
    var rating: Int,
    var votes: Int,
    var max_runtime: Int,
    var size: Int,
    var group_id: Int
)