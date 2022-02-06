package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class RateMovie(
    val token: TokenDto,
    val rating: Rating
)
data class Rating (

    val filterId : Int,
    val movieId : String,
    val like : Boolean
)