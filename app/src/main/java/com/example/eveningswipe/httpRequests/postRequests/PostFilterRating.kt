package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class PostFilterRating (
    val token: TokenDto,
    val filterId: Int
    )